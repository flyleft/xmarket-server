package xmarket.server.entity.transfer;


import com.google.protobuf.InvalidProtocolBufferException;

public class ProtobufSerializeTest {
    @org.junit.Test
    public void testRegisterSerialize() throws InvalidProtocolBufferException{
        byte[] buffer=User.Register.newBuilder().setUsername("小王").setPassword("sdnskdks").setPhone("182003855656546").build().toByteArray();
        for (byte b:buffer){
            System.out.print(b);
        }
        User.Register register=User.Register.parseFrom(buffer);
        System.out.println(register);
    }
}
