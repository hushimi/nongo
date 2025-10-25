package bushigen.nongo.util;

import java.util.List;
import java.util.function.Function;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * EntityからDTOへ変換するHelperクラス
 */
public class DtoConverter {

    /**
     * TをRに変換
     *
     * @param entity 対象Entity
     * @param converter 変換するための関数
     * @param <T> source object
     * @param <R> return object
     */
    public static <T, R> R convert(T source, Function<T, R> converter) {
        if (source == null) {
            return null;
        }

        // execute conversion function on the source
        return converter.apply(source);
    }

    /**
     * Convert one object using a supplier and setter function.
     * Example:
     *   DtoConverter.convert(req, SoftwareEngineer::new, (src, dest) -> {
     *       dest.setName(src.name());
     *       dest.setTechStack(src.techStack());
     *   });
     */
    public static <S, T> T convert(S source, Supplier<T> targetSupplier, BiConsumer<S, T> mappingAction) {
        if (source == null) return null;
        T target = targetSupplier.get();
        mappingAction.accept(source, target);
        return target;
    }

    /**
     * 複数のTをRのリストに変換
     *
     * @param sources 対象Entityの配列
     * @param converter 変換するための関数
     * @param <T> the entity type
     * @param <R> the DTO type
     */
    public static <T, R> List<R> convertList(List<T> sources, Function<T, R> converter) {
        if (sources == null) {
            return null;
        }

        return sources.stream()
            .map(converter)
            .collect(Collectors.toList());
    }

    /**
     * Convert list using supplier and setter function.
     */
    public static <S, T> List<T> convertList(List<S> sources, Supplier<T> targetSupplier, BiConsumer<S, T> mappingAction) {
        if (sources == null) return List.of();
        return sources.stream()
                .map(src -> {
                    T target = targetSupplier.get();
                    mappingAction.accept(src, target);
                    return target;
                })
                .collect(Collectors.toList());
    }
}
