import com.raquo.laminar.api.L._
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom
import org.scalajs.dom.html

object LaminarDocsParagraphs {
  val app: ReactiveHtmlElement[html.Div] = {
    val nameVar: Var[String] = Var("")
    val surnameVar: Var[String] = Var("")
    val ageVar: Var[String] = Var("")
    val buttonState: Var[Boolean] = Var(true)
    val buttonVar: Var[String] = Var("Save")
    val errorVar: Var[String] = Var("")
    val validityState: Signal[Boolean] = Signal.combineSeq(
      nameVar.signal ::
        surnameVar.signal ::
        ageVar.signal ::
        Nil).map(_.contains(""))

    def inputMaker(inputText: String, nameVariable: Var[String], buttonVariable: Var[Boolean]): ReactiveHtmlElement[html.Div] =
      div(inputText,
        child <-- buttonVariable.signal.map(varValue =>
          if (varValue)
            input(typ := "text",
              value <-- nameVariable,
              onChange.mapToValue --> nameVariable)
          else
            span(child.text <-- nameVariable)
        ))

    def compareIfAnyEmpty(varList: List[Var[String]]): Boolean = varList.map(_.now()).contains("")

    div(
      inputMaker("Name: ", nameVar, buttonState),
      inputMaker("Surname: ", surnameVar, buttonState),
      inputMaker("Age: ", ageVar, buttonState),
      button(child.text <-- buttonVar,
        onClick.mapTo(
          if (compareIfAnyEmpty(List(nameVar, surnameVar, ageVar)))
            "ERROR: input field values are flawed"
          else "") --> errorVar,
        onClick.mapTo(
          if (compareIfAnyEmpty(List(nameVar, surnameVar, ageVar)))
            buttonState.now()
          else !buttonState.now()) --> buttonState,
        onClick.mapTo(if (buttonState.now()) "Save" else "Edit") --> buttonVar),
      p(child.text <-- errorVar)
    )}
}
object App {
  val appContainer: dom.Element = dom.document.querySelector("#app-container")

  val rootElement: Div = div(
    LaminarDocsParagraphs.app
  )

  val root: RootNode = render(appContainer, rootElement)

  def main(args: Array[String]): Unit = {
  }
}
