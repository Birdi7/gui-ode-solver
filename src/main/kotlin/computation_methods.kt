import javafx.beans.property.SimpleBooleanProperty
import tornadofx.*

object ComputationalMethodsManager {
    val listOfMethods: MutableMap<String, ComputationalMethod> = mutableMapOf()
    val initialValues = InitialValuesInfo()
    val totalErrorInfo = TotalErrorData()

    init {
        initMethod(EulerMethod())
//        initMethod(ImprovedEulerMethod())
//        initMethod(RungeKuttaMethod())
    }

    fun compute(name: String): MutableMap<Double, Double> {
        return listOfMethods[name]!!.computeFor(initialValues)
    }

    fun computeLocalErrors(name: String): MutableMap<Double, Double> {
        return listOfMethods[name]!!.computeLocalErrors(initialValues)
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
    abstract fun computeLocalErrors(initialValues: InitialValuesInfo): MutableMap<Double, Double>
}

class EulerMethod : ComputationalMethod() {
    override val name = "Euler method"

    override fun computeFor(initialValues: InitialValuesInfo): MutableMap<Double, Double> {
        val result = mutableMapOf<Double, Double>()
        var currentX = initialValues.x0
        var currentY = initialValues.y0
        result[currentX] = currentY

        for (i in 0..initialValues.numberOfSteps) {
            currentY += initialValues.h * initialValues.initialFunction.computeFor(currentX, currentY)
            currentX += initialValues.h
            result[currentX] = currentY
        }
        return result
    }

    override fun computeLocalErrors(initialValues: InitialValuesInfo): MutableMap<Double, Double> {
        val result = mutableMapOf<Double, Double>()
        val computed_result = computeFor(initialValues)
        for ((x, y) in computed_result) {
            result[x] = ExactSolution.computeFor(x) - y
        }
        return result
    }
}

class ImprovedEulerMethod : ComputationalMethod() {
    override val name = "Improved Euler method"

    override fun computeFor(initialValues: InitialValuesInfo): MutableMap<Double, Double> {

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun computeLocalErrors(initialValues: InitialValuesInfo): MutableMap<Double, Double> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class RungeKuttaMethod : ComputationalMethod() {
    override val name = "Runge-Kutta method"

    override fun computeFor(initialValues: InitialValuesInfo): MutableMap<Double, Double> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun computeLocalErrors(initialValues: InitialValuesInfo): MutableMap<Double, Double> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
