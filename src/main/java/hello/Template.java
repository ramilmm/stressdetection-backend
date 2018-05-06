package hello;

public class Template {

    private Integer hrAverage;
    private Integer gsrAverage;
    private Double hrDispersion;
    private Double gsrDispersion;

    public Template() {}

    public Integer getHrAverage() {
        return hrAverage;
    }

    public void setHrAverage(Integer hrAverage) {
        this.hrAverage = hrAverage;
    }

    public Integer getGsrAverage() {
        return gsrAverage;
    }

    public void setGsrAverage(Integer gsrAverage) {
        this.gsrAverage = gsrAverage;
    }

    public Double getHrDispersion() {
        return hrDispersion;
    }

    public void setHrDispersion(Double hrDispersion) {
        this.hrDispersion = hrDispersion;
    }

    public Double getGsrDispersion() {
        return gsrDispersion;
    }

    public void setGsrDispersion(Double gsrDispersion) {
        this.gsrDispersion = gsrDispersion;
    }

    @Override
    public String toString() {
        return "Template{" +
                "hrAverage=" + hrAverage +
                ", gsrAverage=" + gsrAverage +
                ", hrDispersion=" + hrDispersion +
                ", gsrDispersion=" + gsrDispersion +
                '}';
    }
}
