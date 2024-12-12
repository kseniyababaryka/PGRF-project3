import objectdata.Cube;
import objectdata.Jehlan;
import objectdata.Scene;
import rasterdata.RasterBI;
import rasterops.Liner;
import rasterops.Render3DLine;
import rasterops.TrivialLiner;
import transforms.Camera;
import transforms.Mat4PerspRH;
import transforms.Vec2D;
import transforms.Vec3D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * trida pro kresleni na platno: zobrazeni pixelu
 *
 * @author PGRF FIM UHK
 * @version 2020
 */

public class Canvas3D {

        private JFrame frame;
        private JPanel panel;
        private RasterBI img;
        private Liner liner;
        private Scene scene;
        private Render3DLine renderer3D;
        private Camera camera;

        private int x, y;

        public Canvas3D(int width, int height) {
            frame = new JFrame();

            frame.setLayout(new BorderLayout());
            frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
            frame.setResizable(false);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            img = new RasterBI(width, height);
            liner = new TrivialLiner();
            scene = new Scene();
            scene.add(new Cube());
            scene.add(new Jehlan());
            renderer3D = new Render3DLine();
            Vec3D observerPosition = new Vec3D(2, 3, -4);
            camera = new Camera()
                    .withPosition(observerPosition)
                    .withAzimuth(azimuthToOrigin(observerPosition))
                    .withZenith(zenithToOrigin(observerPosition));

            panel = new JPanel() {
                private static final long serialVersionUID = 1L;

                @Override
                public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    present(g);
                }
            };

            panel.setPreferredSize(new Dimension(width, height));

            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    x = e.getX();
                    y = e.getY();
                }
            });

            panel.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    double dx = x - e.getX();
                    double dy = y - e.getY();
                    camera = camera.addAzimuth(dx / 100).addZenith(dy / 100);
                    x = e.getX();
                    y = e.getY();
                    draw();
                }
            });

            frame.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    super.keyPressed(e);

                    if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP){
                        camera = camera.addZenith((double) 1 / 100);
                        draw();
                    }
                    if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN){
                        camera = camera.addZenith((double) -1 / 100);
                        draw();
                    }
                    if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT){
                        camera = camera.addAzimuth((double) 1 / 100);
                        draw();
                    }
                    if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT){
                        camera = camera.addAzimuth((double) -1 / 100);
                        draw();
                    }
                }
            }
            );

            frame.add(panel, BorderLayout.CENTER);
            frame.pack();
            frame.setVisible(true);
        }

        public void clear() {
            img.clear(0x2f2f2f);
        }

        public void present(Graphics graphics) {
            img.present(graphics);
        }

        private double azimuthToOrigin(Vec3D observerPosition) {
            Vec3D viewVector = observerPosition.opposite();
            return viewVector
                    .ignoreZ()
                    .normalized()
                    .map(viewNormalized -> {
                        double angle = Math.acos(viewNormalized.dot(new Vec2D(1, 0)));
                        return (viewNormalized.getY() > 0? angle: 2 * Math.PI - angle);
                    }).orElse(0.0)
                    ;
        }

        private double zenithToOrigin(Vec3D observerPosition) {  // [-PI / 2, PI / 2]
            Vec3D viewVector = observerPosition.opposite();
            return viewVector
                    .normalized()
                    .flatMap(view -> view.withZ(0).normalized().map(projection -> {
                        double angle = Math.acos(view.dot(projection));
                        return (view.getZ() > 0) ? angle : -angle;
                    }))
                    .orElse(0.0);
        }

        public void draw() {
            clear();
            renderer3D.renderScene(img,
                    scene,
                    camera.getViewMatrix(),
                    new Mat4PerspRH(Math.PI / 2, (double) img.height() / img.width(), 0.01, 100),
                    liner,
                    0xff0000);
            panel.repaint();
        }

        public void start() {
            draw();
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> new Canvas3D(800, 800).start());
        }


}
