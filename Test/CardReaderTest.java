import ca.powerutility.NoPowerException;
import com.jimmyselectronics.AbstractDevice;
import com.jimmyselectronics.AbstractDeviceListener;
import com.jimmyselectronics.opeechee.Card;
import com.jimmyselectronics.opeechee.CardReader;
import com.jimmyselectronics.opeechee.CardReaderListener;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import java.io.IOException;
import ca.ucalgary.seng300.simulation.SimulationException;
import ca.ucalgary.seng300.simulation.NullPointerSimulationException;




public class CardReaderTest {
    private CardReader reader;
    private Card card;
    private boolean cardIsInserted;


    @Before
    public void setup() {
        reader = new CardReader();
        reader.plugIn();
        reader.turnOn();

        card = new Card("visa", "1234", "Thanos","567", "789",true, true );
        cardIsInserted = false;
    }


    @Test(expected = NoPowerException.class)
    public void testCardReaderPower() throws NoPowerException {
        //Turn off card reader
        reader.turnOff();
        reader.insert(card,"789");
    }

    @Test(expected = NoPowerException.class)
    public void testCardReaderUnplug() throws NoPowerException{
        reader.unplug();
        reader.insert(card, "789");
    }

    @Test(expected = IllegalStateException.class)
    public void testCardInserted() throws IllegalStateException{
        cardIsInserted = true;
        reader.insert(card, "789");
    }

    @Test
    public void testCardInserted1(){
        reader.insert(card, "789");
        Assert.assertTrue(cardIsInserted);
    }

    @Test(expected = NoPowerException.class)
    public void testRemovePower() throws NoPowerException{
        reader.turnOff();
        reader.remove();

    }
    @Test(expected = NullPointerSimulationException)
    public void testRemoveNoCard() throws NullPointerSimulationException{
        cardIsInserted = false;
        reader.remove();
    }

    @Test
    public void testRemove(){
        cardIsInserted = true;
        reader.remove();
        Assert.assertFalse(cardIsInserted);
    }

    @Test
    public void testCardReaderListener(){
        try{
            CardReaderListener donkeyNoListen = new CardReaderListener() {
                @Override
                public void cardInserted(CardReader reader) {
                    cardIsInserted = true;
                }

                @Override
                public void cardRemoved(CardReader reader) {
                    cardIsInserted = false;
                }


                @Override
                public void cardDataRead(CardReader reader, Card.CardData data) {

                }

                @Override
                public void enabled(AbstractDevice<? extends AbstractDeviceListener> device) {
                    // TODO Auto-generated method stub


                }

                @Override
                public void disabled(AbstractDevice<? extends AbstractDeviceListener> device) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void turnedOn(AbstractDevice<? extends AbstractDeviceListener> device) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void turnedOff(AbstractDevice<? extends AbstractDeviceListener> device) {
                    // TODO Auto-generated method stub

                }
            };
            reader.register(donkeyNoListen);
            reader.insert(card,"789");
            System.out.println("Card:" + card);
            assertEquals(true,cardIsInserted);


    }


}
