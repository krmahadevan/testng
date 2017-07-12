package org.testng.internal;

import org.testng.ITestNGMethod;

import java.util.Comparator;

public final class Systematiser {

    private Systematiser() {
        //Utility class. Defeat instantiation.
    }

    public static Comparator<ITestNGMethod> getComparator() {
        Comparator<ITestNGMethod> comparator;
        String text = System.getProperty("testng.order", Order.INSTANCES.getValue());

        Order order = Order.parse(text);
        switch (order) {
            case METHOD_NAMES:
                comparator = new Comparator<ITestNGMethod>() {
                    @Override
                    public int compare(ITestNGMethod o1, ITestNGMethod o2) {
                        String n1 = o1.getMethodName();
                        String n2 = o1.getMethodName();
                        return n1.compareTo(n2);
                    }

                    @Override
                    public String toString() {
                        return "Method_Names";
                    }
                };
                break;

            case NONE:
                //Disables sorting by providing a dummy comparator which always regards two elements as equal.
                comparator = new Comparator<ITestNGMethod>() {
                    @Override
                    public int compare(ITestNGMethod o1, ITestNGMethod o2) {
                        return 0;
                    }

                    @Override
                    public String toString() {
                        return "No_Sorting";
                    }
                };
                break;

            default:
            case INSTANCES:
                comparator = new Comparator<ITestNGMethod>() {
                    @Override
                    public int compare(ITestNGMethod o1, ITestNGMethod o2) {
                        return o1.toString().compareTo(o2.toString());
                    }

                    @Override
                    public String toString() {
                        return "Instance_Names";
                    }
                };

        }

        return comparator;
    }

    enum Order {
        METHOD_NAMES("methods"),
        INSTANCES("instances"),
        NONE("none");

        Order(String value) {
            this.value = value;
        }

        private String value;

        public String getValue() {
            return value;
        }

        public static Order parse(String value) {
            if (value == null || value.trim().isEmpty()) {
                return INSTANCES;
            }
            for (Order each : values()) {
                if (each.getValue().equalsIgnoreCase(value)) {
                    return each;
                }
            }
            return INSTANCES;
        }
    }
}
