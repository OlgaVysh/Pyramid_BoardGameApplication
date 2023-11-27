import entity.Player
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

/**
 * Test cases for the class [Player]
 */
class PlayerTest {

    /**
     * Tests whether an error is reported when creating a player with an empty name
     */
    @Test
    fun testEmptyName()
    {

        assertFailsWith<IllegalArgumentException>(
            block={ Player("") }
        )

    }
}