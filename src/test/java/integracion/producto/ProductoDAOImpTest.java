package integracion.producto;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import integracion.transaction.Transaction;
import integracion.transactionManager.TransactionManager;
import negocio.producto.TProductoCalzado;
import negocio.producto.TProductoTextil;
import org.junit.jupiter.api.*;

import negocio.producto.TProducto;

class ProductoDAOImpTest {
	private static Connection conn;
	private ProductoDAOImp productoDAOImp;
	private TProducto producto1;
	private TProducto producto2;

	private static Transaction t;

	@BeforeAll
	static void beforeAll() {
		t = TransactionManager.getInstancia().createTransaction();

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/trescerotres", "empleado", "password");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@BeforeEach
	void BeforeEach() {
		try(Statement st=conn.createStatement()){
			st.execute("DELETE FROM producto");
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		productoDAOImp =new ProductoDAOImp();
		producto1 = new TProductoCalzado(0, "Zapatillas", 10, 50, 40, true);
		producto2 = new TProductoTextil(0, "camiseta", 2, 10, "tela", true);
	}
	
	@Test
	void testInsertar() {
		try {

			productoDAOImp.insertar(producto1);
			TProducto productoAux=productoDAOImp.mostrar(producto1.getId());
			assertTrue(iguales(producto1,productoAux));
	
		} catch (Exception e) {
			fail("Excepcion al insertar");
		}	}

	@Test
	void testMostrarTodos() {
		List<TProducto> lista= new ArrayList<TProducto>();
		lista.add(producto1);
		lista.add(producto2);
		try {
			for (TProducto tProducto : lista) {
				productoDAOImp.insertar(tProducto);
			}
	
			List<TProducto> tp = productoDAOImp.mostrarTodos();
	
			for (int i = 0; i < lista.size(); i++) {
				if (!iguales(lista.get(i), tp.get(i))) {
					fail("El producto leido no se corresponde con el insertado");
				}
			}
		} catch (Exception e) {
			fail("Excepcion al mostrar todos");
		}
	}

	@Test
	void testModificar() {
		try {
			productoDAOImp.insertar(producto1);
			
			producto1.setCantidad(70);
			producto1.setPrecio(12f);
			producto1.setNombre("producto modificado");
	
			productoDAOImp.modificar(producto1);
			TProducto productoMod = productoDAOImp.mostrar(producto1.getId());
	
			assertTrue(iguales(producto1, productoMod));
		} catch (Exception e) {
			fail("Excepcion al modificar");
		}
	}	

	@Test
	void testEliminar() {
		try {
			productoDAOImp.insertar(producto1);
	
			productoDAOImp.eliminar(producto1.getId());
			assertFalse(productoDAOImp.mostrar(producto1.getId()).isActivo());	
		} catch (Exception e) {
			fail("Excepcion al eliminar");
		}
		}

	@AfterEach
	 void afterEach(){
		t.commit();
	}

	@AfterAll
	static void afterAll() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	private boolean iguales(TProducto p1, TProducto p2) {
		return p1.getCantidad()==p2.getCantidad()&&
				p1.getId()==p2.getId()&&
				p1.getNombre().equals(p2.getNombre()) &&
				p1.getPrecio()==p2.getPrecio();
	}

}