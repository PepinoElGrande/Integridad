import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Administrador extends Agenda{

	
	
	ArrayList<Contacto> agenda;

	
	public Administrador(ArrayList<Contacto> agenda) {
		this.agenda = agenda;
	}
	
public void agregarUsuario() {
	// tic 
	Scanner entrada = new Scanner(System.in);
	File usuarioruta =new File ("usuario.txt");

	String Usuarioruta=usuarioruta.getAbsolutePath();
	
	
	String ruta = ""+Usuarioruta;
	String usuario, password, rol;
	System.out.print("Introduce nombre usuario: ");
	usuario = entrada.nextLine();
	System.out.print("Introduce nombre contraseña: ");
	password = entrada.nextLine();
	System.out.print("Introduce nombre rol: ");
	rol =  entrada.nextLine();
	// toc
	// comprobar el rol
	//tic
	FileWriter fichero;
	try {
		fichero = new FileWriter(ruta, true);
		BufferedWriter bw = new BufferedWriter(fichero);
		 
		bw.write(usuario + ";" + password + ";"+rol+"\n");
		bw.close();
		fichero.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	// toc
}
	
	
}
