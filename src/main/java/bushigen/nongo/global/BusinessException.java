package bushigen.nongo.global;

/**
 * サービスクラスで発生したExceptionをキャッチする
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
