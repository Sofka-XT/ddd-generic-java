package co.com.sofka.business;

import co.com.sofka.business.generic.UseCase;

public class UseCaseTest extends UseCase<UseCaseTest.Request, UseCaseTest.Response> {

    @Override
    public void executeUseCase(Request input) {
        emit().onSuccess(new Response("Hello wold from use case -> " + input.getArgs()));
    }

    public static class Request implements UseCase.RequestValues{
        private String args;
        public Request(String args) {
            this.args = args;
        }
        public String getArgs() {
            return args;
        }

        public void setArgs(String args) {
            this.args = args;
        }

    }

    public static class Response implements UseCase.ResponseValues {
        private String status;
        public Response(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }


    }
}
