package uz.feliza.felizabackend.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldNameConstants;

//@Getter
//@RequiredArgsConstructor
@FieldNameConstants(onlyExplicitlyIncluded = true)
public enum RoleName {
    @FieldNameConstants.Include CUSTOMER,
    @FieldNameConstants.Include ADMIN,
    @FieldNameConstants.Include SALESPERSON,
    @FieldNameConstants.Include EDITOR,
    @FieldNameConstants.Include SHIPPER,
    @FieldNameConstants.Include ASSISTANT

//    CUSTOMER(
//            Set.of(CUSTOMER_READ,
//                    CUSTOMER_CREATE,
//                    CUSTOMER_UPDATE,
//                    CUSTOMER_DELETE
//                    )
//    ),
//    ADMIN(
//            Set.of(
//                    ADMIN_READ,
//                    ADMIN_CREATE,
//                    ADMIN_UPDATE,
//                    ADMIN_DELETE,
//                    CUSTOMER_READ,
//                    CUSTOMER_CREATE,
//                    CUSTOMER_UPDATE,
//                    CUSTOMER_DELETE
//            )
//    ),
//    SALESPERSON(
//            Set.of(
//                    SALESPERSON_READ,
//                    SALESPERSON_UPDATE
//            )),
//    EDITOR(
//            Set.of(
//                    EDITOR_READ,
//                    EDITOR_UPDATE
//            )
//    ),
//    SHIPPER(
//            Set.of(
//                    SHIPPER_READ,
//                    SHIPPER_UPDATE
//            )
//    ),
//    ASSISTANT(
//            Set.of(
//                    ASSISTANT_READ,
//                    ASSISTANT_UPDATE
//            )
//    );

//    private final Set<Permission> permissions;

//    public List<SimpleGrantedAuthority> getAuthorities(){
//        var authorities = getPermissions()
//                .stream()
//                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
//                .collect(Collectors.toList());
//        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
//        return authorities;
//    }
}
