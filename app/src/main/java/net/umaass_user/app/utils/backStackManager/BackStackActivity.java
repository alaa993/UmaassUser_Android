/*
 * Copyright (C) 2016 JetRadar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.umaass_user.app.utils.backStackManager;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import net.umaass_user.app.ui.base.BaseActivity;


public abstract class BackStackActivity extends BaseActivity {

    private static final String STATE_BACK_STACK_MANAGER = "back_stack_manager";

    static protected BackStackManager backStackManager;
    static FragmentManager fragmentManager;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        backStackManager = new BackStackManager();
        fragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void onDestroy() {
        backStackManager = null;
        super.onDestroy();
    }

    public BackStackManager getBackStackManager() {
        return backStackManager;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (backStackManager != null) {
            outState.putParcelable(STATE_BACK_STACK_MANAGER, backStackManager.saveState());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (backStackManager != null) {
            backStackManager.restoreState(savedInstanceState.getParcelable(STATE_BACK_STACK_MANAGER));
        }
    }

    /**
     * @return false if failed to put fragment in back stack. Relates to issue:
     * java.lang.IllegalStateException: Fragment is not currently in the FragmentManager at
     * android.support.v4.app.FragmentManagerImpl.saveFragmentInstanceState(FragmentManager.java:702)
     */
    protected static boolean pushFragmentToBackStack(int hostId, @NonNull Fragment fragment) {
        try {
            BackStackEntry entry = BackStackEntry.create(fragmentManager, fragment);
            backStackManager.push(hostId, entry);
            return true;
        } catch (Exception e) {
            Log.e("MultiBackStack", "Failed to add fragment to back stack", e);
            return false;
        }
    }

    @Nullable
    protected Fragment popFragmentFromBackStack(int hostId) {
        BackStackEntry entry = backStackManager != null ? backStackManager.pop(hostId) : null;
        return entry != null ? entry.toFragment(this) : null;
    }

    @Nullable
    protected Pair<Integer, Fragment> popFragmentFromBackStack() {
        Pair<Integer, BackStackEntry> pair = backStackManager != null ? backStackManager.pop() : null;
        return pair != null ? Pair.create(pair.first, pair.second.toFragment(this)) : null;
    }

    /**
     * @return false if back stack is missing.
     */
    protected boolean resetBackStackToRoot(int hostId) {
        return backStackManager.resetToRoot(hostId);
    }

    /**
     * @return false if back stack is missing.
     */
    protected boolean clearBackStack(int hostId) {
        return backStackManager.clear(hostId);
    }

    /**
     * @return the number of fragments in back stack.
     */
    protected int backStackSize(int hostId) {
        return backStackManager.backStackSize(hostId);
    }


}