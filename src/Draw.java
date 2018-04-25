import org.json.JSONException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;

public class Draw extends JPanel implements ActionListener {

    private int bottom = View.getWindowH();

    private point leadPoint;
    private point trailPoint;

    private point costPos = new point(20, 30);
    private point balPos = new point(20, 20);

    private double cost = 0;
    private int resetNum = 0;
    private int increment = 0;

    public Draw() {
        setSize(800, 600);
        setCost();
        resetPoints();

        Timer clock = new Timer(100, this);
        clock.start();
    }

    public void setCost() {
        try{
            this.cost = JsonReader.getPrice();}
        catch(IOException ioE){}
        catch(JSONException jsonE){}
    }

    public void resetPoints(){
        leadPoint = new point(0,(int)cost);
        trailPoint = new point(0,(int)cost);
    }
    public void paintComponent(Graphics g){
        //paint occurs when the method repaint() is called (see actionPerformed method )
        Graphics2D g2d = (Graphics2D) g;


        if(leadPoint.getX() > 800)
        {
            super.paintComponent(g2d);
            resetPoints();


            resetNum++;
        }

        //set bull/bear color

        g2d.setColor(new Color(47,49,49));
        g2d.fillRect(0,0,800,50);

        if(leadPoint.getY()>trailPoint.getY())g.setColor(Color.RED);
        if(leadPoint.getY()<trailPoint.getY())g.setColor(new Color(8,200, 133));

        g2d.drawLine(leadPoint.getX(), leadPoint.getY(), trailPoint.getX(), trailPoint.getY());
        g2d.setColor(Color.WHITE);


        g2d.drawString("Last Price:  $"+cost, costPos.getX(), costPos.getY());
        g2d.drawString("Inc: "+increment, balPos.getX(), balPos.getY());
//        g2d.drawString("USD: $"+bal, balPos.getX(), balPos.getY());

    }//end paint

    private void updateVectors() {




        double x = leadPoint.getX();
        double y = leadPoint.getY();

        trailPoint.setXY((int)x,(int)y);
        x+=4;


        //setting up autoscroll when pricechanges
        //if(cost)
        //increment += 5;

        y = cost;

        leadPoint.setXY((int)x,(int)y);

    }//end updateVectors

    public void actionPerformed(ActionEvent arg0) {
        repaint();
        updateVectors();
    }

}
