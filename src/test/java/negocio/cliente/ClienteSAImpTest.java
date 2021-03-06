package negocio.cliente;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import integracion.transaction.Transaction;
import integracion.transactionManager.TransactionManager;
import org.junit.jupiter.api.*;

import integracion.cliente.ClienteDAOImp;

class ClienteSAImpTest {

	private static Connection conn;
	private TCliente cliente1;
	private TCliente cliente2;
	private ClienteSAImp clienteSAImp;

	@BeforeAll
	static void beforeAll() {

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/trescerotres", "empleado", "password");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@BeforeEach
	void BeforeEach() {
		try(Statement st=conn.createStatement()){
			st.execute("DELETE FROM cliente");
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		 clienteSAImp =new ClienteSAImp();
		 cliente1 = new TCliente();
		 cliente2 = new TCliente();
		 
		 // Cliente 1
		 cliente1.setActivo(true);
		 cliente1.setFecha_registro(LocalDate.now());
		 cliente1.setNombre("Jose");
		 //Cliente 2
		 cliente2.setActivo(true);
		 cliente2.setFecha_registro(LocalDate.now());
		 cliente2.setNombre("Dani");
	}
	
	@Test
	void testInsertar() {
		try {
			clienteSAImp.insertar(cliente1);
			TCliente clienteAux=clienteSAImp.mostrar(cliente1.getId());
			assertTrue(iguales(cliente1,clienteAux));
	
		} catch (Exception e) {
			fail("Excepcion al insertar");
		}
		
	}


	@Test
	void testMostrarTodos() {
		List<TCliente> lista= new ArrayList<TCliente>();
		lista.add(cliente1);
		lista.add(cliente2);
		try {
			for (TCliente tCliente : lista) {
				clienteSAImp.insertar(tCliente);
			}
	
			List<TCliente> tc = clienteSAImp.mostrarTodos();
	
			for (int i = 0; i < lista.size(); i++) {
				if (!iguales(lista.get(i), tc.get(i))) {
					fail("El cliente leido no se corresponde con el insertado");
				}
			}
		} catch (Exception e) {
			fail("Excepcion al mostrar todos");
		}
	}

	@Test
	void testModificar() {
		try {
			clienteSAImp.insertar(cliente1);
	
			 cliente1.setActivo(true);
			 cliente1.setFecha_registro(LocalDate.now());
			 cliente1.setNombre("Jose Modificado");
	
			 clienteSAImp.modificar(cliente1);
			TCliente clienteMod = clienteSAImp.mostrar(cliente1.getId());
	
			assertTrue(iguales(cliente1, clienteMod));
		} catch (Exception e) {
			fail("Excepcion al modificar");
		}
	}

	@Test
	void testEliminar() {
		try {
			clienteSAImp.insertar(cliente1);
	
			clienteSAImp.eliminar(cliente1.getId());
			assertFalse(clienteSAImp.mostrar(cliente1.getId()).isActivo());	
		} catch (Exception e) {
			fail("Excepcion al eliminar");
		}
	}


	@AfterAll
	static void afterAll() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private boolean iguales(TCliente c1, TCliente c2) {
		return c1.getId()==c2.getId()&&
				c1.getFecha_registro().equals(c2.getFecha_registro())&&
				c1.getNombre().equals(c2.getNombre());
	}
}
