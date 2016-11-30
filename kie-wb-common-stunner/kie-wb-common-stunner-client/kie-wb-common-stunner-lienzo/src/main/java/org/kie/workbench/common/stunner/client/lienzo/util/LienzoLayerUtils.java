/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.workbench.common.stunner.client.lienzo.util;

import com.ait.lienzo.client.core.shape.Shape;
import org.kie.workbench.common.stunner.client.lienzo.LienzoLayer;
import org.kie.workbench.common.stunner.core.client.shape.view.ShapeView;

public class LienzoLayerUtils {

    public static String getUUID_At( final LienzoLayer lienzoLayer, final double x, final double y ) {
        int sx = ( int ) x;
        int sy = ( int ) y;
        final Shape<?> shape = lienzoLayer.getLienzoLayer().getLayer().findShapeAtPoint( sx, sy );
        return getShapeUUID( shape );
    }

    private static String getShapeUUID( final Shape<?> lienzoShape ) {
        if ( null != lienzoShape ) {
            if ( hasUUID( lienzoShape ) ) {
                return getNodeViewUUID( lienzoShape );
            }
            com.ait.lienzo.client.core.shape.Node<?> parent = lienzoShape.getParent();
            while ( null != parent && !hasUUID( parent ) ) {
                parent = parent.getParent();
            }
            if ( null != parent ) {
                return getNodeViewUUID( parent );
            }
        }
        return null;
    }

    private static boolean hasUUID( final com.ait.lienzo.client.core.shape.Node<?> node ) {
        return node != null && node.getUserData() != null && ( node.getUserData() instanceof String ) &&
                ( ( ( String ) node.getUserData() ).startsWith( ShapeView.UUID_PREFIX ) );
    }

    private static String getNodeViewUUID( final com.ait.lienzo.client.core.shape.Node<?> node ) {
        final String userData = ( String ) node.getUserData();
        return userData.substring( ShapeView.UUID_PREFIX.length(), userData.length() - 1 );
    }
}
