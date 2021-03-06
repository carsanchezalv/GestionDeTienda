package presentacion.command.factura;

import negocio.factoriaSA.FactoriaSA;
import negocio.factura.FacturaSA;
import presentacion.command.Command;
import presentacion.controladorAplicacion.Context;
import presentacion.controladorAplicacion.EventosFactura;

public class CerrarFacturaCommand implements Command{

	@Override
	public Context execute(Object datos) {
		String mensaje;
		int id = (int) datos;
		FacturaSA facturaSA = FactoriaSA.getInstancia().generaFacturaSA();
		
		try{
			facturaSA.eliminar(id);
			return new Context(EventosFactura.CERRAR_FACTURA_OK, null);
		} catch(Exception e){
			mensaje = e.getMessage();
			return new Context(EventosFactura.CERRAR_FACTURA_KO, mensaje);
		}
	}
}