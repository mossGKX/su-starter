@TypeDefs(value = {@TypeDef(name = "Json", typeClass = JsonUserType.class),
    @TypeDef(name = "JsonList", typeClass = JsonListUserType.class),
    @TypeDef(name = "UuidEntityList", typeClass = UuidEntityListUserType.class)})
package com.cntest.su.jpa.usertype;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

