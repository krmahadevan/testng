package org.testng.internal;

import org.testng.ITestClassInstance;

/**
 * Represents the ability to retrieve the parameters associated with a factory method.
 *
 * @deprecated - This interface stands deprecated as of TestNG <code>7.11.0</code>.
 */
@Deprecated
public interface IParameterInfo<T> extends ITestClassInstance<T> {
  /**
   * @return - The parameters associated with the factory method as an array.
   * @deprecated - This method stands deprecated as of TestNG <code>7.11.0</code> Please use {@link
   *     ITestClassInstance#getFactoryMethod()} to retrieve the parameters.
   */
  @Deprecated
  Object[] getParameters();
}
