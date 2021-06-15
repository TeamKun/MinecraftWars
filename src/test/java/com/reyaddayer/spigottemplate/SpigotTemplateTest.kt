package com.reyaddayer.spigottemplate

import be.seeseemelk.mockbukkit.MockBukkit
import com.reyadayer.spigottemplate.SpigotTemplate
import org.junit.jupiter.api.*


internal class SpigotTemplateTest {

    companion object{
        @BeforeAll
        @JvmStatic
        fun load() {
            MockBukkit.mock()
        }

        @AfterAll
        @JvmStatic
        fun unload() {
            MockBukkit.unmock()
        }
    }

    @Test
    fun test() {
        val plugin = MockBukkit.createMockPlugin()
        Assertions.assertEquals("MockPlugin", plugin.name)
    }
}