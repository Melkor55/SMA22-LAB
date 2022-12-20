package model;

public class MonthlyExpenses {
    public String month;
    private float income, expenses;

    public MonthlyExpenses() {
        //Default constructor
    }

    public MonthlyExpenses(String month, float income, float expenses) {
        this.month = month;
        this.income = income;
        this.expenses = expenses;
    }

    public String getMonth() {
        return month;
    }

    public float getIncome() {
        return income;
    }

    public float getExpenses() {
        return expenses;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return "MonthlyExpenses{" +
                "month='" + month + '\'' +
                ", income=" + income +
                ", expenses=" + expenses +
                '}';
    }

    public void setIncome(float income) {
        this.income = income;
    }

    public void setExpenses(float expenses) {
        this.expenses = expenses;
    }
}
