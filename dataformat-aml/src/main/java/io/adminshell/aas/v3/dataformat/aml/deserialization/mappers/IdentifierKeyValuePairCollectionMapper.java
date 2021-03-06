/*
 * Copyright (c) 2021 Fraunhofer-Gesellschaft zur Foerderung der angewandten Forschung e. V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.adminshell.aas.v3.dataformat.aml.deserialization.mappers;

import io.adminshell.aas.v3.dataformat.aml.deserialization.AmlParser;
import io.adminshell.aas.v3.dataformat.aml.deserialization.DefaultMapper;
import io.adminshell.aas.v3.dataformat.aml.deserialization.MappingContext;
import io.adminshell.aas.v3.dataformat.aml.model.caex.AttributeType;
import io.adminshell.aas.v3.dataformat.aml.model.caex.CAEXObject;
import io.adminshell.aas.v3.dataformat.core.util.AasUtils;
import io.adminshell.aas.v3.dataformat.mapping.MappingException;
import io.adminshell.aas.v3.model.IdentifierKeyValuePair;
import java.beans.PropertyDescriptor;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IdentifierKeyValuePairCollectionMapper extends DefaultMapper<Collection<IdentifierKeyValuePair>> {

    @Override
    protected Collection mapCollectionValueProperty(AmlParser parser, MappingContext context) throws MappingException {
        Collection result = new ArrayList<>();
        CAEXObject current = parser.getCurrent();
        AttributeType parent = findAttribute(current, context.getProperty(), context);
        if (parent == null) {
            return result;
        }
        List<AttributeType> attributes = findAttributes(parent, x -> x.getName().startsWith("specificAssetId"));
        for (AttributeType attribute : attributes) {
            parser.setCurrent(attribute);
            try {
                IdentifierKeyValuePair element = context.getTypeFactory().newInstance(IdentifierKeyValuePair.class);
                for (PropertyDescriptor property : AasUtils.getAasProperties(IdentifierKeyValuePair.class)) {
                    Object propertyValue = context
                            .with(element)
                            .with(property)
                            .withoutType()
                            .map(property.getReadMethod().getGenericReturnType(), parser);
                    if (propertyValue != null) {
                        try {
                            property.getWriteMethod().invoke(element, propertyValue);
                        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                            throw new MappingException(String.format("error setting property value for property %s", property.getName()), ex);
                        }
                    }
                }
                parser.setCurrent(attribute);
                result.add(element);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(IdentifierKeyValuePairCollectionMapper.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(IdentifierKeyValuePairCollectionMapper.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(IdentifierKeyValuePairCollectionMapper.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(IdentifierKeyValuePairCollectionMapper.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(IdentifierKeyValuePairCollectionMapper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        parser.setCurrent(parent);
        return result;
    }

}
