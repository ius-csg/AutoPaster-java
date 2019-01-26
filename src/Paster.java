import java.awt.*;
import java.awt.event.KeyEvent;

public class Paster implements Runnable
{
    public String textToPrint;

    Paster(String textToPrint) {
        this.textToPrint = textToPrint;
    }

    @Override
    public void run()
    {
        try {
            Thread.sleep(2000);
            pressKeys(this.textToPrint);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void pressKeys(String keysCombination) throws IllegalArgumentException
    {
        try {
            Robot robot = new Robot();
            for (String key : keysCombination.split(""))
            {
                try
                {   System.out.println(key);
                    int event;
                    if(key.equals(" "))
                        event = KeyEvent.VK_SPACE;
                    else
                        event = (int) KeyEvent.class.getField("VK_" + key.toUpperCase()).getInt(null);

                    robot.keyPress(event);
                    robot.keyRelease(event);

                } catch (IllegalAccessException e)
                {
                    e.printStackTrace();

                }catch(NoSuchFieldException e )
                {
                    throw new IllegalArgumentException(key.toUpperCase()+" is invalid key\n"+"VK_"+key.toUpperCase() + " is not defined in java.awt.event.KeyEvent");
                }
            }
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
