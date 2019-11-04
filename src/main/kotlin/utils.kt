import javafx.beans.property.Property
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.scene.chart.LineChart
import javafx.scene.chart.NumberAxis
import javafx.scene.layout.VBox
import tornadofx.*
import kotlin.math.exp

object InitialValueFunction {
    override fun toString(): String = "y'=5-x^2 - y^2 + 2xy"
    fun computeFor(x: Double, y: Double): Double = 5 - x * x - y * y + 2 * x * y
}

class InitialValuesInfo(
    x0: Double = 0.0,
    y0: Double = 1.0,
    xMax: Double = 20.0,
    numberOfSteps: Int = 40
) {
    private val x0Property = SimpleDoubleProperty(x0)
    var x0 by x0Property
    private val y0Property = SimpleDoubleProperty(y0)
    var y0 by y0Property
    private val xMaxProperty = SimpleDoubleProperty(xMax)
    var xMax by xMaxProperty
    private val numberOfStepsProperty = SimpleIntegerProperty(numberOfSteps)
    var numberOfSteps by numberOfStepsProperty

    val h: Double = (xMax - x0) / numberOfSteps.toDouble()
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
    override fun toString(): String = "(x+3*e^(4x)*(x+2)-2) / (3*e^(4x)+1)"
    fun computeFor(x: Double) = (x + 3 * exp(4 * x) * (x + 2) - 2) / (3 * exp(4 * x) + 1)

    fun computeFor(initialValuesInfo: InitialValuesInfo): MutableMap<Double, Double> {
        val result = mutableMapOf<Double, Double>()
        var x = initialValuesInfo.x0
        while (x < initialValuesInfo.xMax) {
            result[x] = computeFor(x)
            x += initialValuesInfo.h
        }
        return result
    }
}


object ChartGenerator {
    fun getVboxWithAll(): VBox {
        val result = VBox()
        result += generateSolutionsChart()
        result += generateLocalErrorsChart()
        return result
    }

    fun generateSolutionsChart(): LineChart<Number, Number> {
        val v = LineChart<Number, Number>(NumberAxis(), NumberAxis())
        v.series("Exact") {
            for ((x, y) in ExactSolution.computeFor(ComputationalMethodsManager.initialValues)) {
                this.data(x, y)
            }
        }

        for (name in ComputationalMethodsManager.listOfMethods.filter { it.value.isSelected }.keys) {
            v.series(name) {
                for ((x, y) in ComputationalMethodsManager.compute(name)) {
                    this.data(x, y)
                }
            }
        }
        return v
    }

    fun generateLocalErrorsChart(): LineChart<Number, Number> {
        val v = LineChart<Number, Number>(NumberAxis(), NumberAxis())
        for (name in ComputationalMethodsManager.listOfMethods.filter { it.value.isSelected }.keys) {
            v.series(name) {
                for ((x, y) in ComputationalMethodsManager.computeLocalErrors(name)) {
                    this.data(x, y)
                }
            }
        }
        return v
    }
}
