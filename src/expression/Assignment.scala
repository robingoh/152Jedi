package expression

/**
  * Created by robingoh on 11/27/17.
  */
case class Assignment(val variable: Identifier,
                      val update: Expression)
  extends SpecialForm {


}
