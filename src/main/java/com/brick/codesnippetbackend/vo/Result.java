package com.brick.codesnippetbackend.vo;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Component
public class Result<T>
{
    private int code;
    private T data;

    Result()
    {

    }

    public Result(int code, T data)
    {
        this.code = code;
        this.data = data;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public T getData()
    {
        return data;
    }

    public void setData(T data)
    {
        this.data = data;
    }
}
