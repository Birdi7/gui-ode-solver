import javafx.beans.property.SimpleBooleanProperty
import tornadofx.getValue
import tornadofx.setValue
import java.lang.Math.abs
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set

object ComputationalMethodsManager {
    val listOfMethods: MutableMap<String, ComputationalMethod> = mutableMapOf()
    val initialValues = InitialValuesInfo()
    val totalErrorInfo = TotalErrorData()

    init {
        initMethod(EulerMethod())
        initMethod(ImprovedEulerMethod())
        initMethod(RungeKuttaMethod())
    }

    fun getSelectedMethods(): Map<String, ComputationalMethod> {
        return listOfMethods.filter { it.value.isSelected }
    }

    fun compute(name: String, iniValues: InitialValuesInfo = initialValues): MutableMap<Double, Double> {
        return listOfMethods[name]!!.computeFor(iniValues)
    }

    fun computeLocalErrors(name: String, iniValues: InitialValuesInfo = initialValues): MutableMap<Double, Double> {
        return listOfMethods[name]!!.computeLocalErrors(iniValues)
    }

    fun computeGlobalErrors(
        name: String,
        iniValues: InitialValuesInfo = initialValues,
        data: TotalErrorData = totalErrorInfo
    ): MutableMap<Double, Double> {
        return listOfMethods[name]!!.computeGlobalErrors(initialValues, data)
    }

    fun initMethod(method: ComputationalMethod) {
        listOfMethods.put(method.name, method)
    }
}

abstract class ComputationalMethod {
    abstract val name: String
    var isSelectedProperty = SimpleBooleanProperty(true)
    var isSelected by isSelectedProperty

    abstract fun computeFor(initialValues: InitialValuesInfo): MutableMap<Double, Double>

    fun computeLocalErrors(initialValues: InitialValuesInfo): MutableMap<Double, Double> {
        val result = mutableMapOf<Double, Double>()
        val computed_result = computeFor(initialValues)
        for ((x, y) in computed_result) {
            result[x] = abs(ExactSolution.computeFor(x) - y)
        }
        return result
    }

    fun computeGlobalErrors(initialValues: InitialValuesInfo, errorData: TotalErrorData): MutableMap<Double, Double> {
        return mutableMapOf()
    }
}

class EulerMethod : ComputationalMethod() {
    override val name = "Euler method"

    override fun computeFor(initialValues: InitialValuesInfo): MutableMap<Double, Double> {
        val result = mutableMapOf<Double, Double>()
        var currentX = initialValues.x0
        var currentY = initialValues.y0
        result[currentX] = currentY

        for (i in 0 until initialValues.numberOfSteps) {
            currentY += initialValues.h * initialValues.initialFunction.computeFor(currentX, currentY)
            currentX += initialValues.h
            result[currentX] = currentY
        }
        return result
    }
}

class ImprovedEulerMethod : ComputationalMethod() {
    override val name = "Improved Euler method"

    override fun computeFor(initialValues: InitialValuesInfo): MutableMap<Double, Double> {
        val result = mutableMapOf<Double, Double>()
        var currentX = initialValues.x0
        var currentY = initialValues.y0
        result[currentX] = currentY

        for (i in 0 until initialValues.numberOfSteps) {
            val h = initialValues.h

            val k1 = initialValues.initialFunction.computeFor(currentX, currentY)
            val k2 = initialValues.initialFunction.computeFor(currentX + h, currentY + h * k1)
            currentY += h / 2.0 * (k1 + k2)
            currentX += initialValues.h
            result[currentX] = currentY
        }
        return result
    }
}

class RungeKuttaMethod : ComputationalMethod() {
    override val name = "Runge-Kutta method"

    override fun computeFor(initialValues: InitialValuesInfo): MutableMap<Double, Double> {
        val result = mutableMapOf<Double, Double>()
        var currentX = initialValues.x0
        var currentY = initialValues.y0
        result[currentX] = currentY

        for (i in 0 until initialValues.numberOfSteps) {
            val h = initialValues.h

            val k1 = initialValues.initialFunction.computeFor(currentX, currentY)
            val k2 = initialValues.initialFunction.computeFor(currentX + h / 2, currentY + h / 2 * k1)
            val k3 = initialValues.initialFunction.computeFor(currentX + h / 2, currentY + h / 2 * k2)
            val k4 = initialValues.initialFunction.computeFor(currentX + h, currentY + h * k3)

            currentY += h / 6 * (k1 + 2 * k2 + 2 * k3 + k4)
            currentX += initialValues.h
            result[currentX] = currentY
        }
        return result
    }
}


// todo: if x0 is not endpoint????
// p.131 from textbook