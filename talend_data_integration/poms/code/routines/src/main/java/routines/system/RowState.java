// ============================================================================
//
// Copyright (C) 2006-2021 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package routines.system;

public class RowState {

    Exception e = null;

    public RowState() {

    }

    public Exception getException() {
        return e;
    }

    public void setException(Exception e) {
        if (this.e == null) {
            this.e = e;
        }
    }

    public void reset() {
        e = null;
    }
}
