import objectdata.Point2D;
import rasterdata.RasterBI;
import rasterops.Liner;
import rasterops.Polygoner;
import objectdata.Polygon;
import rasterops.TrivialLiner;
import transforms.Mat3Transl2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Canvas {
    private JFrame frame;
    private JPanel panel;

    private RasterBI img;

    private int currentX;
    private int currentY;
    private int c1, r1, c2, r2, x, y;


    private objectdata.Polygon polygon;
    private Polygoner polygoner;

    public Canvas(int width, int height) {
        frame = new JFrame();

        frame.setLayout(new BorderLayout());
        frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        img = new RasterBI(width, height);
        polygoner = new Polygoner();
        polygon = new Polygon();
        TrivialLiner liner = new TrivialLiner();




        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g);
            }
        };

        panel.setPreferredSize(new Dimension(width, height));
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                clear();
                if (e.getKeyCode() == KeyEvent.VK_S) {
//                    polygon = polygon.translate(0, 1);
//                    polygon = polygon.transforms(point2D -> point2D.translate(0, 1));
                    polygon = polygon.transformed(new Mat3Transl2D(0, 1));
                } else if (e.getKeyCode() == KeyEvent.VK_P) {
//                    polygon = polygon.scale(2);
                    polygon = polygon.transforms(point2D -> point2D.scale(2));
                } else if (e.getKeyCode() == KeyEvent.VK_N) {
                    polygon = polygon.scale(0.5);
                } else if (e.getKeyCode() == KeyEvent.VK_R) {
                    polygon = polygon
                            .translate(-polygon.getPoint(2).getX(), -polygon.getPoint(2).getY())
                            .rotate(Math.PI / 100)
                            .translate(polygon.getPoint(2).getX(), polygon.getPoint(2).getY());
                }
                polygoner.draw(img, polygon, liner, 0xffff00);
                panel.repaint();
            }
        });


        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                c1 = e.getX();
                r1 = e.getY();

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                x = e.getX();
                y = e.getY();

                polygon.addPoint(new Point2D(e.getX(), e.getY()));
                panel.repaint();
            }
        });


        frame.addMouseMotionListener(new MouseMotionAdapter(){
            @Override
            public void mouseDragged(MouseEvent e) {
                clear();

                c2 = e.getX();
                r2 = e.getY();
                polygoner.draw(img, polygon, liner, 0x000000);
                if (polygon.size() > 0) {
                    liner.draw(img, (int) polygon.getPoint(polygon.size() - 1).getX(),
                            (int) polygon.getPoint(polygon.size() - 1).getY(),
                            c2, r2, 0x000000);
                }
                liner.draw(img, (int) polygon.getPoint(0).getX(), (int) polygon.getPoint(0).getY(), c2, r2, 0x000000);
                panel.repaint();
            }



        });


        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    img.setColor(currentX + 1, currentY, 0xff0000);
                    panel.repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN){
                    img.setColor(currentX -1, currentY, 0xff0000);
                    panel.repaint();
                }
            }
        });

        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    public void clear() {
        img.clear(0xf2f2f2);
    }

    public void present(Graphics graphics) {
        img.present(graphics);
    }

    public void draw() {
        clear();

        img.present(frame.getGraphics());

        img.setColor(100,100, 0xff0000);
        currentX = 100;
        currentY = 100;




//        for (int i = img.getWidth()/4; i < (img.getWidth()/4)*3; i++) {
//            for (int j = img.getHeight()/4; j < (img.getHeight()/4)*3; j++) {
//                img.setRGB(i, j, 0x0000ff);
//            }
//        }
//        int i = 0;
//        int j = 0;
//        while(i < img.getWidth() && j < img.getHeight()) {
//            img.setRGB(i, j, 0x00ff00);
//            i++;
//            j++;
//        }
//        int a = 0;
//        int b = img.getHeight();
//        while(a < img.getWidth() && b > 0) {
//            img.setRGB(a, b, 0x00ff00);
//            a++;
//            b--;
//        }
    }

    public void start() {
        draw();
        panel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Canvas(800, 800).start());


    }
}
