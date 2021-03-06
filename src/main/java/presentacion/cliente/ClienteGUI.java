package presentacion.cliente;

import javax.swing.*;
import java.awt.*;

public interface ClienteGUI {
	public abstract void initialize();

	public abstract String getName();

	public abstract void createPathButton(String name);

	public abstract void addPathSeparator();

	public abstract JButton createMenuButton(String iconPath, Color color);

	public abstract void showOutputMsg(JPanel area, JLabel text, String msg,
			Boolean ok);

	public abstract void anadirPanel();

	public abstract void editarPanel();

	public abstract void mostrarPanel();

	public abstract void buscarPanel();

	public abstract void clear();

	public abstract void mostrar();

	public abstract void actualizar(int evento, Object datos);
}