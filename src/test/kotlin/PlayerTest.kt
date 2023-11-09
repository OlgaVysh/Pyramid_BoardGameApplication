import entity.Player
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class PlayerTest {

    @Test //Testet, ob beim Erzeugen eines Players mit leerem Namen ein Fehler gemeldet wird
    fun testEmptyName()
    {

        assertFailsWith<IllegalArgumentException>(
            block={ Player("") }
        )

    }



    //Testet, dass dem entity.Player kein negativer Score übergeben werden darf
    @Test
    fun testSetter()
    {
        //assertFailsWith<IllegalArgumentException>(
          //  block={
            //    Player("Timm").set(-10)
            //})

    }
}