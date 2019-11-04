import javafx.stage.Stage
import tornadofx.*

class MyApp: App(MyView::class) {
    override fun start(stage: Stage) {
        stage.isResizable = false
        super.start(stage)
    }
}

class MyView: View() {
    override val root = form {
        borderpane {
            left = vbox {
                fieldset("Initial values") {
                    // todo: add textfields with links to properties of objects
                }
                fieldset("Values for Max error") {
                    // todo: add min_n max_n text fields
                }
                button("Plot")
            }
            right = vbox {
                fieldset {
                    // todo: add charts
                }
            }
        }
    }
}
fun main(args: Array<String>) {
    launch<MyApp>(args)
}