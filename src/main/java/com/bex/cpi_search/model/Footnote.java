package com.bex.cpi_search.model;

/** Represents a footnote in the CPI search application. */
public class Footnote {

  /** The code that uniquely identifies the footnote. */
  private String code;

  /** The descriptive text of the footnote. */
  private String text;

  /**
   * Gets the code of the footnote.
   *
   * @return the code of the footnote
   */
  public String getCode() {
    return code;
  }

  /**
   * Sets the code of the footnote.
   *
   * @param codeValue the code to set
   */
  public void setCode(final String codeValue) {
    this.code = codeValue;
  }

  /**
   * Gets the text of the footnote.
   *
   * @return the text of the footnote
   */
  public String getText() {
    return text;
  }

  /**
   * Sets the text of the footnote.
   *
   * @param textValue the text to set
   */
  public void setText(final String textValue) {
    this.text = textValue;
  }

  @Override
  public String toString() {
    return "Footnote{" + "code='" + code + '\'' + ", text='" + text + '\'' + '}';
  }
}
