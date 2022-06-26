package poll;

public interface visitor<C> {
    public Double visit(BusinessVoting<C> gpl);
    public Double visit(DinnerOrder<C> gpl);
    public Double visit(Election<C> gpl);
}
