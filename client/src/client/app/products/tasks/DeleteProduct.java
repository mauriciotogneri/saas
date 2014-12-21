package client.app.products.tasks;

import share.app.products.Product;
import client.app.products.gui.def.GUIDeleteProduct;
import client.app.products.operations.OperationsProducts;
import client.core.gui.taks.OptionTask;

public class DeleteProduct extends OptionTask<Boolean>
{
	private final Product product;
	
	public DeleteProduct(Product product)
	{
		super(GUIDeleteProduct.PATH, TaskType.SINGLE);
		
		this.product = product;
	}
	
	@Override
	public void start()
	{
		if (showConfirmLiteral(getLiteral(GUIDeleteProduct.Literals.ASK_DELETE, this.product.name)))
		{
			Boolean response = OperationsProducts.call().deleteProduct(this.product);
			close(valid(response));
		}
		else
		{
			close(false);
		}
	}
}