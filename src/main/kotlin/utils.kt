import javafx.beans.property.Property
import tornadofx.toProperty

object InitialValueFunction {
    override fun toString(): String = "y'=5-x^2 - y^2 + 2xy"
    fun computeFor(x: Double, y: Double): Double = 5 - x * x - y * y + 2 * x * y
}

data class InitialValuesInfo(
    var x0: Double = 0.0,
    var y0: Double = 1.0,
    var xMax: Double = 20.0,
    var numberOfSteps: Int = 10
) {
    private val x0Property = x0.toProperty()
    private val y0Property = y0.toProperty()
    private val xMaxProperty = xMax.toProperty()
    private val numberOfStepsProperty = numberOfSteps.toProperty()

    val h: Double = (xMax - x0) / numberOfSteps
    val initialFunction = InitialValueFunction

    val mapOfProperties = mapOf<String, Property<Number>>(
        "x0" to x0Property,
        "y0" to y0Property,
        "xMax" to xMaxProperty,
        "n" to numberOfStepsProperty
    )
}

data class TotalErrorData(var n0: Int = 1, var nMax: Int = 10) {
    val n0Property = n0.toProperty()
    val nMaxProperty = nMax.toProperty()

    val mapOfProperties = mapOf(
        "n0" to n0Property,
        "nMax" to nMaxProperty
    )
}

object ExactSolution {
    override fun toString(): String = "1/(0.75*e^(4x)-0.25) + x + 2"
    fun computeFor(x: Double) = 1.0 / (0.75 * Math.exp(4 * x) - 0.25) + x + 2
}
