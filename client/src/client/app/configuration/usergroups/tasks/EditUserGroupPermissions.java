package client.app.configuration.usergroups.tasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import share.core.objects.Permission;
import share.core.objects.Task;
import share.core.objects.UserGroup;
import client.app.configuration.usergroups.gui.def.GUIEditUserGroupPermissions;
import client.app.configuration.usergroups.operations.OperationsPermissions;
import client.core.gui.OptionTask;

public class EditUserGroupPermissions extends OptionTask<Void> {
	
	private UserGroup userGroup = null;
	private ArrayList<Task> generalList = new ArrayList<Task>();
	
	private GUIEditUserGroupPermissions gui = new GUIEditUserGroupPermissions();
	
	public EditUserGroupPermissions(UserGroup userGroup) {
		super(GUIEditUserGroupPermissions.PATH, TaskType.MODAL);
		
		this.userGroup = userGroup;
	}
	
	public void start() {
		setGUI(this.gui);
		addTitle(this.userGroup.name);
		setGeneralList();
		refreshUsersGroupPermisions();
	}
	
	private void setGeneralList() {
		Task[] list = OperationsPermissions.call().getTasks();
		
		for (Task task : list) {
			this.generalList.add(task);
		}
	}
	
	private void refreshUsersGroupPermisions() {
		Permission[] userList = sortPermissions(OperationsPermissions.call().getUserGroupPermissions(this.userGroup.id));
		
		this.gui.list_in.setRows(userList);
		this.gui.list_out.setRows(filterGeneralList(userList));
	}
	
	private Permission[] sortPermissions(Permission[] original) {
		ArrayList<Permission> list = new ArrayList<Permission>();
		
		for (Permission permission : original) {
			list.add(permission);
		}
		
		Collections.sort(list, new PermissionComparator());
		
		Permission[] result = new Permission[original.length];
		list.toArray(result);
		
		return result;
	}
	
	private Task[] filterGeneralList(Permission[] userList) {
		ArrayList<Task> list = new ArrayList<Task>();
		
		for (Task task : this.generalList) {
			if (!inUserGroupList(task, userList)) {
				list.add(task);
			}
		}
		
		Task[] result = new Task[list.size()];
		list.toArray(result);
		
		return result;
	}
	
	private boolean inUserGroupList(Task task, Permission[] userList) {
		boolean valid = false;
		
		for (Permission permission : userList) {
			if (permission.task == task.id) {
				valid = true;
				break;
			}
		}
		
		return valid;
	}
	
	private void addPermission() {
		if (this.gui.list_out.isRowSelected()) {
			
			Task current = (Task)this.gui.list_out.getCurrentRow();
			Permission permission = new Permission(0, this.userGroup.id, current.id, current.name, current.path);
			boolean response = OperationsPermissions.call().addUserGroupPermission(permission);
			
			if (response) {
				refreshUsersGroupPermisions();
			} else {
				showWarning(GUIEditUserGroupPermissions.Literals.PREMISSION_NOT_ADDED);
			}
			
		} else {
			showWarning(GUIEditUserGroupPermissions.Literals.ROW_NOT_SELECTED);
		}
		
		this.gui.list_out.focus();
	}
	
	private void deletePermission() {
		if (this.gui.list_in.isRowSelected()) {
			
			Permission current = (Permission)this.gui.list_in.getCurrentRow();
			boolean response = OperationsPermissions.call().deleteUserGroupPermission(current);
			
			if (response) {
				refreshUsersGroupPermisions();
			} else {
				showWarning(GUIEditUserGroupPermissions.Literals.PREMISSION_NOT_DELETED);
			}
			
		} else {
			showWarning(GUIEditUserGroupPermissions.Literals.ROW_NOT_SELECTED);
		}
		
		this.gui.list_out.focus();
	}
	
	private void cleanIn() {
		this.gui.list_in.cleanSearch();
	}
	
	private void cleanOut() {
		this.gui.list_out.cleanSearch();
	}
	
	public void event(Event event) {
		switch (event) {
		
			case ADD:
				addPermission();
				break;
			
			case DELETE:
				deletePermission();
				break;
			
			case CLEAR_IN:
				cleanIn();
				break;
			
			case CLEAR_OUT:
				cleanOut();
				break;
			
			default:
				break;
		}
	}
	
	class PermissionComparator implements Comparator<Permission> {
		
		public int compare(Permission permissionA, Permission permissionB) {
			return permissionA.taskName.compareTo(permissionB.taskName);
		}
	}
}