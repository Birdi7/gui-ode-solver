object ComputationalMethodsManager {
    val listOfMethods: MutableMap<String, ComputationalMethod> = mutableMapOf()
    val initialValues = InitialValuesInfo()
    val totalErrorInfo = TotalErrorData()

    init {
        initMethod(EulerMethod())
        initMethod(ImprovedEulerMethod())
        initMethod(RungeKuttaMethod())
    }

    fun compute(name: String): MutableMap<Double, Double> {
        return listOfMethods[name]!!.compute(initialValues)
    }

    fun initMethod(method: ComputationalMethod) {
        listOfMethods.put(method.name, method)
    }
}

abstract class ComputationalMethod {
    abstract val name: String
    abstract fun compute(initialValues: InitialValuesInfo): MutableMap<Double, Double>
    abstract fun compute_local_errors(initialValues: InitialValuesInfo): MutableList<Double>
}

class EulerMethod : ComputationalMethod() {
    override val name = "Euler method"

    override fun compute(initialValues: InitialValuesInfo): MutableMap<Double, Double> {
        val result = mutableMapOf<Double, Double>()
        var previousX = initialValues.x0
        var previousY = initialValues.y0

        for (i in 0..initialValues.numberOfSteps) {
            val newX = previousX + initialValues.h
            val newY = previousY + initialValues.h * initialValues.initialFunction.computeFor(previousX, previousY)
            result[newX] = newY
            previousX = newX
            previousY = newY
        }
        return result
    }

    override fun compute_local_errors(initialValues: InitialValuesInfo): MutableList<Double> {
        val result = mutableListOf<Double>()
        val computed_result = compute(initialValues)
        for (pair in computed_result) {
            result.add(ExactSolution.computeFor(pair.key) - pair.value)
        }
        return result
    }
}

class ImprovedEulerMethod : ComputationalMethod() {
    override val name = "Improved Euler method"

    override fun compute(initialValues: InitialValuesInfo): MutableMap<Double, Double> {

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun compute_local_errors(initialValues: InitialValuesInfo): MutableList<Double> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class RungeKuttaMethod : ComputationalMethod() {
    override val name = "Runge-Kutta method"

    override fun compute(initialValues: InitialValuesInfo): MutableMap<Double, Double> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun compute_local_errors(initialValues: InitialValuesInfo): MutableList<Double> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
