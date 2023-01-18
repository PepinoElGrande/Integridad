import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.Date;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		leerMD5();
		escribirMD5();
		// log in
		Scanner entrada = new Scanner(System.in);
		String usuario, password, rol = "";
		System.out.println("Introduce usuario");
		usuario = entrada.nextLine();
		System.out.println("Introduce contraseña");
		password = entrada.nextLine();
		// leo fichero usuarios

		// preguntar por la llave

		// descifrar
		
		File usuarioruta =new File ("usuarios.txt");

		String Usuarioruta=usuarioruta.getAbsolutePath();
		
		
		String ruta = ""+Usuarioruta;


		FileReader fichero;
		try {
			fichero = new FileReader(ruta);
			BufferedReader br = new BufferedReader(fichero);
			String linea = "";
			while ((linea = br.readLine()) != null) {
				String[] datos = linea.split(";");
				if (usuario.equals(datos[0])) {
					if (password.equals(datos[1])) {
						rol = datos[2];
						break;
					}

				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// cifrar los usuarios

		// preguntas por la llave / contraseña
		// leo la agenda

		File agendaruta =new File ("agenda.txt");

		String Agendaruta=agendaruta.getAbsolutePath();
		

		
		ruta = ""+Agendaruta;
		Agenda agenda = new Agenda();


		try {	
			fichero = new FileReader(ruta);
			BufferedReader br = new BufferedReader(fichero);
			String linea = "";
			int i = 0;
			String nombre = "", apellidos = "", direccion = "", telefono = "";
			while ((linea = br.readLine()) != null) {
				if (i == 0) {
					nombre = linea;
					i++;
				} else if (i == 1) {
					apellidos = linea;
					i++;
				} else if (i == 2) {
					direccion = linea;
					i++;
				} else if (i == 3) {
					telefono = linea;
					agenda.agenda.add(new Contacto(nombre, apellidos, direccion, telefono));
				
					i = 0;
				}

				// registrarActividad
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// regitrarError();
		}

		// cifras la agenda

		Administrador admin = null;
		Gestor gestor = null;
		Asistente asistente = null;

		switch (rol) {
		case "admin":
			admin = new Administrador(agenda.agenda);
			System.out.println("el usuario es un admin");
			// escibir en traza admin usuario alfredo log in ok como rol de admin
			break;
		case "gestor":
			gestor = new Gestor(agenda.agenda);
			System.out.println("el usuario es un gestor");
			break;
		case "asistente":
			asistente = new Asistente(agenda.agenda);
			break;
		default:
			System.out.println("USUARIO NO VALIDO");

			break;
		}

		// menu usuario
		System.out.println("1. Add contacto");
		System.out.println("2. Agregar usuaio");
		System.out.println("3. Ver fallo de integridad si cambias usuarios.txt");

		int opcion = entrada.nextInt();
		// registro todso los nuemros
		String info = "";
		Date fecha;
		switch (opcion) {
		case 1:
			switch (rol) {
			case "admin":
				admin.addContact();
				break;
			case "asistente":
				asistente.addContact();
				break;
			case "gestor":
				gestor.addContact();
				break;
			}

			fecha = new Date();
			info = fecha + ";" + usuario + ";" + rol + ";addContact()";
			
			File actividadruta =new File ("actividad.txt");

			String Actividadruta=actividadruta.getAbsolutePath();
			

			
			ruta = ""+Actividadruta;
			registrar(ruta, info);
			// registro el tipo de accion elegida
			generarMD5(info,fecha);
			break;
		
		case 2:
			System.out.println("CASO 2");
			switch (rol) {
			case "admin":
				admin.agregarUsuario();
				break;
			case "asistente":
				System.out.println("No tienes permiso para agregar un usuario");
				break;
			case "gestor":
				System.out.println("No tienes permiso para agregar un usuario");
				break;
			}
			break;
		
		case 3:
			
			fecha = new Date();
			info = fecha + ";" + usuario + ";" + rol + ";listarContacto()";
			registrar(ruta, info);
			generarMD5(info,fecha);
			break;
		}
		
		
		leerMD5();

	}

	public static void registrarActividad(String info) {
		File actividadruta =new File ("actividad.txt");

		String Actividadruta=actividadruta.getAbsolutePath();
		

		
		String ruta = ""+Actividadruta;
		FileWriter fichero;
		try {
			fichero = new FileWriter(ruta, true);
			BufferedWriter bw = new BufferedWriter(fichero);
			bw.write(info + "\n");
			bw.close();
			fichero.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void registrar(String ruta, String info) {

		FileWriter fichero;
		try {
			fichero = new FileWriter(ruta, true);
			BufferedWriter bw = new BufferedWriter(fichero);
			bw.write(info + "\n");
			bw.close();
			fichero.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

	
	
	public static void generarMD5(String mensaje, Date fecha) {
		String salida = "";
		  try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(mensaje.getBytes(),0,mensaje.length());
				BigInteger bi = new BigInteger(1,md.digest());
				salida = bi.toString(16);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		  File respaldoruta =new File ("respaldo.txt");

			String Respaldoruta=respaldoruta.getAbsolutePath();
			

			
			String ruta = ""+Respaldoruta;
	
		  FileWriter fichero;
		
			try {
				fichero = new FileWriter(ruta, true);
				BufferedWriter bw = new BufferedWriter(fichero);
				bw.write(fecha + ";" + salida + "\n");
				bw.close();
				fichero.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public static String generarMD5(String mensaje) {
		String salida = "";
		  try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(mensaje.getBytes(),0,mensaje.length());
				BigInteger bi = new BigInteger(1,md.digest());
				salida = bi.toString(16);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  return salida;
	}
	public static void escribirMD5() {
		  File usuariosruta =new File ("usuarios.txt");

			String Usuariosruta=usuariosruta.getAbsolutePath();
			

			
			String ruta = ""+Usuariosruta;
			
			 File usuarios_respaldoruta =new File ("usuarios_respaldo.txt");

				String Usuarios_respaldoruta=usuarios_respaldoruta.getAbsolutePath();
				

				
				String ruta2 = ""+Usuarios_respaldoruta;
		

//				System.out.println(ruta+ruta2);
		
		 FileReader fichero;
			String codigo = "";
			String linea ="";
			try {
				fichero = new FileReader(ruta);
				BufferedReader br = new BufferedReader(fichero);
				int i  = 0;
				while ((linea = br.readLine()) != null) {
					codigo = generarMD5(linea);
					 FileWriter fichero2;
						
						try {
							if(i == 0) {
								fichero2 = new FileWriter(ruta2);
								i++;
							}else {
								fichero2 = new FileWriter(ruta2,true);
							}
						
							BufferedWriter bw = new BufferedWriter(fichero2);
							bw.write(codigo + "\n");
							bw.close();
							fichero2.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
				
				br.close();
				fichero.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	
		
		
		
		
		  
		 
	}
	
public static void leerMD5() {
		
	 File usuariosruta =new File ("usuarios.txt");

		String Usuariosruta=usuariosruta.getAbsolutePath();
		

		
		String ruta = ""+Usuariosruta;
		
		 File usuarios_respaldoruta =new File ("usuarios_respaldo.txt");

			String Usuarios_respaldoruta=usuarios_respaldoruta.getAbsolutePath();
			

			
			String ruta2 = ""+Usuarios_respaldoruta;
	
//		System.out.println(ruta+ruta2);
		 FileReader fichero;
			FileReader fichero2;
			String codigo = "";
			String linea ="";
			String codigo2  = "";
			int nLinea = 0;
			try {
				fichero = new FileReader(ruta);
							fichero2 = new FileReader(ruta2);
				BufferedReader br = new BufferedReader(fichero);
				BufferedReader br2 = new BufferedReader(fichero2);
			
				while ((linea = br.readLine()) != null) {
					codigo = generarMD5(linea);
					codigo2 = br2.readLine();	
					
					if(codigo.equals(codigo2)) {
						System.out.println("OK");
					}else {
						//eliminar(nLinea);
						//clonar(nLinea);
						//modiicar(nLinea);
						System.out.println("Fallo de integridad en la linea " + nLinea);
					}
							
						nLinea++;
				}
				
				br.close();
				br2.close();
				fichero.close();
				fichero2.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	
		
		
		
		/*
		String ruta = "D:\\Gostudent\\03 Alumnos\\Pepe - UML\\Practicas\\Autorizacion\\respaldo.txt";
		String ruta1 = "D:\\Gostudent\\03 Alumnos\\Pepe - UML\\Practicas\\Autorizacion\\usuarios.txt";
		String ruta2 = "D:\\Gostudent\\03 Alumnos\\Pepe - UML\\Practicas\\Autorizacion\\ageda.txt";
		String ruta3 = "D:\\Gostudent\\03 Alumnos\\Pepe - UML\\Practicas\\Autorizacion\\errores.txt";
		 */
		  
		 
	}
	
}