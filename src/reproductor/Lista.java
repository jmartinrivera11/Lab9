
package reproductor;

public class Lista {
    
    private Cancion cancion;
    
    public Lista() {
        this.cancion = null;
    }
    
    public void addCancion(String nombre, String artista, String duracion, 
            String img, String tipo, String rutaIMG) {
        Cancion newCancion = new Cancion(nombre, artista, duracion, img, tipo, rutaIMG);
        
        if (cancion == null) {
            cancion = newCancion;
        } else {
            Cancion tempCancion = cancion;
            while (tempCancion.nextCancion != null) {
                tempCancion = tempCancion.nextCancion;
            }
            tempCancion.nextCancion = newCancion;
        }
    }
    
    public Cancion seleccionar(String nombre) {
        Cancion tempCancion = cancion;
        
        while (tempCancion != null) {
            if (tempCancion.nombre.equals(nombre)) {
                return tempCancion;
            }
            tempCancion = tempCancion.nextCancion;
        }
        return null;
    }
}
