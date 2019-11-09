import javafx.beans.property.Property
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.scene.chart.LineChart
import javafx.scene.chart.NumberAxis
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import tornadofx.*
import kotlin.math.exp

object InitialValueFunction {
    override fun toString(): String = "y' = 5-x^2 - y^2 + 2xy"
    fun computeFor(x: Double, y: Double): Double = 5 - x * x - y * y + 2 * x * y
}

class InitialValuesInfo(
    x0: Double = 0.0,
    y0: Double = 1.0,
    xMax: Double = 20.0,
    numberOfSteps: Int = 45
) {
    private val x0Property = SimpleDoubleProperty(x0)
    var x0 by x0Property
    private val y0Property = SimpleDoubleProperty(y0)
    var y0 by y0Property
    private val xMaxProperty = SimpleDoubleProperty(xMax)
    var xMax by xMaxProperty
    private val numberOfStepsProperty = SimpleIntegerProperty(numberOfSteps)
    var numberOfSteps by numberOfStepsProperty

    val h: Double
        get() = (xMax - x0) / numberOfSteps.toDouble()

    val initialFunction = InitialValueFunction

    val mapOfProperties = mapOf<String, Property<Number>>(
        "x0" to x0Property,
        "y0" to y0Property,
        "xMax" to xMaxProperty,
        "n" to numberOfStepsProperty
    )

    fun clone(): InitialValuesInfo {
        return InitialValuesInfo(
            x0 = this.x0,
            y0 = this.y0,
            xMax = this.xMax,
            numberOfSteps = this.numberOfSteps
        )
    }
}

class TotalErrorData(n0: Int = 30, nMax: Int = 110) {
    val n0Property = SimpleIntegerProperty(n0)
    var n0 by n0Property
    val nMaxProperty = SimpleIntegerProperty(nMax)
    var nMax by nMaxProperty

    val mapOfProperties = mapOf(
        "n0" to n0Property,
        "nMax" to nMaxProperty
    )
}

object ExactSolution {
    override fun toString(): String = "1/(const1 * e^(4x) - 0.25) + x + 2"
    private val constant
        get() = (exp(-4 * ComputationalMethodsManager.initialValues.x0) *
                (0.25 + 1 /
                        (ComputationalMethodsManager.initialValues.y0 -
                                ComputationalMethodsManager.initialValues.x0 - 2))
                )


    fun computeFor(x: Double) = 1 / (constant * exp(4 * x) - 0.25) + x + 2

    fun computeFor(initialValuesInfo: InitialValuesInfo): MutableMap<Double, Double> {
        val result = mutableMapOf<Double, Double>()
        var x = initialValuesInfo.x0
        while (x <= initialValuesInfo.xMax) {
            result[x] = computeFor(x)
            x += initialValuesInfo.h
        }
        return result
    }
}

object ChartGenerator {
    fun getBoxWithAll(): Pane {
        val result = HBox()
        if (!ensureDataCorrect()) {
            result += Label("Wrong data entered!")
            return result
        }
        result += generateSolutionsChart()
        result += VBox(generateLocalErrorsChart(), generateTotalErrorsChart())
        return result
    }

    fun generateSolutionsChart(): LineChart<Number, Number> {
        val chart = getXYChart()
        chart.title = "Solutions chart"
        for ((name, method) in ComputationalMethodsManager.getSelectedMethods()) {
            chart.series(name) {
                for ((x, y) in ComputationalMethodsManager.compute(name)) {
                    this.data(x, y)
                }
            }
        }
        chart.series("Exact") {
            for ((x, y) in ExactSolution.computeFor(ComputationalMethodsManager.initialValues)) {
                this.data(x, y)
            }
        }
        return chart
    }

    fun generateLocalErrorsChart(): LineChart<Number, Number> {
        val chart = getXYChart()
        chart.title = "Local errors chart"
        for ((name, method) in ComputationalMethodsManager.getSelectedMethods()) {
            chart.series(name) {
                for ((x, y) in ComputationalMethodsManager.computeLocalErrors(name)) {
                    this.data(x, y)
                }
            }
        }
        return chart
    }

    fun generateTotalErrorsChart(): LineChart<Number, Number> {
        val chart = getXYChart("n", "max error")
        chart.title = "Total error chart"
        for ((name, method) in ComputationalMethodsManager.getSelectedMethods()) {
            chart.series(name) {
                for ((n, max_error) in ComputationalMethodsManager.computeGlobalErrors(name)) {
                    this.data(n, max_error)
                }
            }
        }
        return chart
    }

    private fun getXYChart(label1: String = "x", label2: String = "y"): LineChart<Number, Number> {
        val x = NumberAxis()
        x.label = label1
        val y = NumberAxis()
        y.label = label2
        val chart = LineChart<Number, Number>(x, y)
        return chart
    }
}

fun MutableMap<Double, Double>.getMaxByValue(): Double {
    if (this.isEmpty()) return 0.0
    var max: Double = -1.0
    for ((k, v) in this) {
        if (max == -1.0 || v > max) {
            max = v
        }
    }
    return max
}

fun ensureDataCorrect(): Boolean {
    return ensureTotalErrorData() && ensureInitialValuesCorrect()
}

fun ensureTotalErrorData(totalErrorData: TotalErrorData = ComputationalMethodsManager.totalErrorInfo): Boolean {
    return (totalErrorData.n0 <= totalErrorData.nMax)
}

fun ensureInitialValuesCorrect(iniValues: InitialValuesInfo = ComputationalMethodsManager.initialValues): Boolean {
    return (iniValues.x0 <= iniValues.xMax)
}