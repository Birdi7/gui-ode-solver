object ComputationalMethodsManager {
    val listOfMethods: MutableMap<String, ComputationalMethod> = mutableMapOf()

    init {
        initMethod(EulerMethod())
        initMethod(ImprovedEulerMethod())
        initMethod(RungeKuttaMethod())
    }


    fun initMethod(method: ComputationalMethod) {
        listOfMethods.put(method.name, method)
    }
}

abstract class ComputationalMethod {
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
