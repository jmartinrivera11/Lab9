
package reproductor;

public class Cancion {
    
    String nombre;
    String artista;
    String duracion;
    String img;
    String tipo;
    String rutaIMG;
    Cancion nextCancion;

    public Cancion(String nombre, String artista, String duracion, 
            String img, String tipo, String rutaIMG) {
        this.nombre = nombre;
        this.artista = artista;
        this.duracion = duracion;
        this.img = img;
        this.tipo = tipo;
        this.rutaIMG = rutaIMG;
        this.nextCancion = null;
    }
}
