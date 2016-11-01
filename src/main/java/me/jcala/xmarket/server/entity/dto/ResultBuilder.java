package me.jcala.xmarket.server.entity.dto;

public  class ResultBuilder<T> {

    private final Result<T> result=new Result<>();

    private ResultBuilder(){}

    public ResultBuilder<T> Code(int code){
        result.setCode(code);
        return this;
    }

    public ResultBuilder<T> msg(String msg){
        result.setMsg(msg);
        return this;
    }

    public ResultBuilder<T> data(T t){
        result.setData(t);
        return this;
    }

    public Result<T> build(){
        return this.result;
    }
}
