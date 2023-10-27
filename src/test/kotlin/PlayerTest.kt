import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class PlayerTest {

    @Test //Testet, ob beim Erzeugen eines Players mit leerem Namen ein Fehler gemeldet wird
    fun testEmptyName()
    {

        assertFailsWith<IllegalStateException>(
            block={ Player("")}
        )

    }

    @Test
    fun testScore() //Testet, ob beim Erzeugen eines Players mit negativen Punkten ein Fehler gemeldet wird
    {
        assertFailsWith<IllegalStateException>(
            block={ Player("Timm", -8)}
        )

    }

    @Test
    fun testSetter() //Testet den Setter
    {
        val player1 = Player("Olga")
        player1.score = 10
        assertEquals(10, player1.score)
    }
}