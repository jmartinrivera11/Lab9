
package reproductor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class Gui extends JFrame {

    private Lista lista;
    private Reproductor reproductor;
    private JLabel info;
    private JLabel img;
    private DefaultListModel<String> modelo;
    private JList<String> listaRolas;
    private Cancion cancionActual;

    public Gui() {
        lista = new Lista();
        reproductor = new Reproductor();
        cancionActual = null;

        setTitle("SPOTIFY");
        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel panelCanciones = new JPanel();
        panelCanciones.setLayout(new BorderLayout());

        modelo = new DefaultListModel<>();
        listaRolas = new JList<>(modelo);
        JScrollPane scroll = new JScrollPane(listaRolas);
        panelCanciones.add(scroll, BorderLayout.CENTER);
        panelCanciones.setPreferredSize(new Dimension(300, 0));

        JPanel panelBTNS = new JPanel();
        JButton playBtn = new JButton("Play");
        JButton stopBtn = new JButton("Stop");
        JButton pauseBtn = new JButton("Pause");
        JButton resumeBtn = new JButton("Resume");
        JButton addBtn = new JButton("Add Song");

        panelBTNS.add(playBtn);
        panelBTNS.add(stopBtn);
        panelBTNS.add(pauseBtn);
        panelBTNS.add(resumeBtn);
        panelBTNS.add(addBtn);

        info = new JLabel("No hay canción seleccionada");
        img = new JLabel();
        img.setPreferredSize(new Dimension(250, 250));

        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BorderLayout());
        panelInfo.add(info, BorderLayout.NORTH);
        panelInfo.add(img, BorderLayout.CENTER);

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = JOptionPane.showInputDialog("Ingrese el nombre de la canción:");
                String artista = JOptionPane.showInputDialog("Ingrese el artista:");
                String duracion = JOptionPane.showInputDialog("Ingrese la duración (min:seg):");
                String tipo = JOptionPane.showInputDialog("Ingrese el tipo de música:");

                JFileChooser imgChooser = new JFileChooser();
                imgChooser.setDialogTitle("Seleccione la imagen");
                int imgValue = imgChooser.showOpenDialog(null);
                String pathIMG = "";
                if (imgValue == JFileChooser.APPROVE_OPTION) {
                    pathIMG = imgChooser.getSelectedFile().getPath();
                }

                JFileChooser audioChooser = new JFileChooser();
                audioChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                audioChooser.setDialogTitle("Seleccione la canción (SOLO ARCHIVOS .WAV)");
                int audioValue = audioChooser.showOpenDialog(null);
                if (audioValue == JFileChooser.APPROVE_OPTION) {
                    File audioFile = audioChooser.getSelectedFile();
                    String audioPath = audioFile.getPath();
                    
                    if (!audioPath.toLowerCase().endsWith(".wav")) {
                        JOptionPane.showMessageDialog(null, "Solo se permiten archivos .wav", 
                                "Error de formato", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    lista.addCancion(nombre, artista, duracion, pathIMG, tipo, audioPath);
                    modelo.addElement(nombre);
                    info.setText("Canción agregada: " + nombre);
                }
            }
        });
        
        listaRolas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String nombre = listaRolas.getSelectedValue();
                if (nombre != null) {
                    Cancion cancion = lista.seleccionar(nombre);
                    if (cancion != null) {
                        info.setText("<html>Nombre: " + cancion.nombre +
                                "<br>Artista: " + cancion.artista +
                                "<br>Duración: " + cancion.duracion +
                                "<br>Tipo de Música: " + cancion.tipo + "</html>");
                        if (!cancion.img.isEmpty()) {
                            img.setIcon(new ImageIcon(new ImageIcon(cancion.img).getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT)));
                        } else {
                            img.setIcon(null);
                        }
                    }
                }
            }
        });
        
        playBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = listaRolas.getSelectedValue();
                if (nombre != null) {
                    Cancion cancion = lista.seleccionar(nombre);
                    if (cancion != null) {
                        if (cancionActual != null) {
                            reproductor.stop();
                        }
                        cancionActual = cancion;
                        reproductor.play(cancion.rutaIMG);
                    }
                }
            }
        });
        
        stopBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reproductor.stop();
            }
        });
        
        pauseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reproductor.pause();
            }
        });
        
        resumeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reproductor.resume();
            }
        });
        
        add(panelCanciones, BorderLayout.WEST);
        add(panelInfo, BorderLayout.CENTER);
        add(panelBTNS, BorderLayout.SOUTH);

        setVisible(true);
    }
    
    public static void main(String[] args) {
        new Gui();
    }
}
