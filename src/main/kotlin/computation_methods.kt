import tornadofx.*

data class InitialValuesInfo(var x0: Int = 1, var y0: Int = 2, var xMax: Int = 10, var numberOfSteps: Int = 10) {
    val h: Double = (xMax.toDouble() - x0) / numberOfSteps
    val x0Property = x0.toProperty()
    val y0Property = y0.toProperty()
    val xMaxProperty = xMax.toProperty()
    val numberOfStepsProperty = numberOfSteps.toProperty()

    val mapOfProperties = mapOf(
        "x0" to x0Property,
        "y0" to y0Property,
        "xMax" to xMaxProperty,
        "n" to numberOfStepsProperty
    )
}

object ComputationalMethodsManager {
    val listOfMethods: MutableMap<String, ComputationalMethod> = mutableMapOf()
    val initialValues = InitialValuesInfo()

    init {
        initMethod(EulerMethod())
        initMethod(ImprovedEulerMethod())
        initMethod(RungeKuttaMethod())
    }

    fun initMethod(method: ComputationalMethod) {
        listOfMethods.put(method.name, method)
    }
}

abstract class ComputationalMethod(isSelected: Boolean=false) {
    abstract val name: String
}

class EulerMethod: ComputationalMethod() {
    override val name = "Euler method"
}

class ImprovedEulerMethod: ComputationalMethod() {
    override val name = "Improved Euler method"
}

class RungeKuttaMethod: ComputationalMethod() {
    override val name = "Runge-Kutta method"
}

fun main() {
    println(ComputationalMethodsManager.listOfMethods)
}