package net.petrikainulainen.junit5;

import io.appium.java_client.windows.WindowsDriver;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JUnit5ExampleTest {

    private static WindowsDriver CalculatorSession = null;
    private static WebElement CalculatorResult = null;

    @BeforeAll
    public static void setup() {
        try {
            CalculatorSession = new WindowsDriver(new URL("http://127.0.0.1:4723"), getCapabilities());
            CalculatorSession.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            CalculatorResult = CalculatorSession.findElementByAccessibilityId("CalculatorResults");
            Assertions.assertNotNull(CalculatorResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static DesiredCapabilities getCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("app", "Microsoft.WindowsCalculator_8wekyb3d8bbwe!App");
        return capabilities;
    }

    @BeforeEach
    public void Clear() {
        CalculatorSession.findElementByName("Limpar").click();
        assertEquals("Exibição é 0", _GetCalculatorResultText());
    }

    @AfterAll
    public static void TearDown() {
        CalculatorResult = null;
        if (CalculatorSession != null) { CalculatorSession.quit(); }
        CalculatorSession = null;
    }

    @RepeatedTest(10)
    public void Addition() {
        CalculatorSession.findElementByName("Um").click();
        CalculatorSession.findElementByName("Mais").click();
        CalculatorSession.findElementByName("Sete").click();
        CalculatorSession.findElementByName("Igual a").click();
        assertEquals("Exibição é 8", _GetCalculatorResultText());
    }

    @Test
    public void Combination() {
        CalculatorSession.findElementByName("Sete").click();
        CalculatorSession.findElementByName("Multiplicar por").click();
        CalculatorSession.findElementByName("Nove").click();
        CalculatorSession.findElementByName("Mais").click();
        CalculatorSession.findElementByName("Um").click();
        CalculatorSession.findElementByName("Igual a").click();
        CalculatorSession.findElementByName("Dividir por").click();
        CalculatorSession.findElementByName("Oito").click();
        CalculatorSession.findElementByName("Igual a").click();
        assertEquals("Exibição é 8", _GetCalculatorResultText());
    }

    @Test
    public void Division() {
        CalculatorSession.findElementByName("Oito").click();
        CalculatorSession.findElementByName("Oito").click();
        CalculatorSession.findElementByName("Dividir por").click();
        CalculatorSession.findElementByName("Um").click();
        CalculatorSession.findElementByName("Um").click();
        CalculatorSession.findElementByName("Igual a").click();
        assertEquals("Exibição é 8", _GetCalculatorResultText());
    }

    @Test
    public void Multiplication() {
        CalculatorSession.findElementByName("Nove").click();
        CalculatorSession.findElementByName("Multiplicar por").click();
        CalculatorSession.findElementByName("Nove").click();
        CalculatorSession.findElementByName("Igual a").click();
        assertEquals("Exibição é 81", _GetCalculatorResultText());
    }

    @Test
    public void Subtraction() {
        CalculatorSession.findElementByName("Nove").click();
        CalculatorSession.findElementByName("Menos").click();
        CalculatorSession.findElementByName("Um").click();
        CalculatorSession.findElementByName("Igual a").click();
        assertEquals("Exibição é 8", _GetCalculatorResultText());
    }

    protected String _GetCalculatorResultText() {
        // trim extra text and whitespace off of the display value
        return CalculatorResult.getText().replace("Display is", "").trim();
    }
}
