import sqlite3
import tkinter as tk
from tkinter import ttk, messagebox

def connect_db():
    return sqlite3.connect('tft_database.db')

# Function to populate champion list with more details
def populate_champion_list(tree):
    for item in tree.get_children():
        tree.delete(item)
    conn = connect_db()
    cursor = conn.cursor()
    cursor.execute("""
        SELECT name, ability, ad, ap, attack_speed, hp, armor, magic_resist, mana, manaCost, attackRange, cost
        FROM Champions
    """)
    for row in cursor.fetchall():
        tree.insert('', tk.END, values=row)
    conn.close()

# Function to populate player list
def populate_player_list(tree):
    for item in tree.get_children():
        tree.delete(item)
    conn = connect_db()
    cursor = conn.cursor()
    cursor.execute("SELECT playerID, name, winrate, teamName FROM Player")
    players = cursor.fetchall()
    for player in players:
        playerID, name, winrate, teamName = player
        # Fetch components
        cursor.execute("""
            SELECT GROUP_CONCAT(c.name, ', ') FROM Components c
            JOIN Player_components pc ON c.componentID = pc.componentID
            WHERE pc.playerID = ?
        """, (playerID,))
        components = cursor.fetchone()[0] or ''
        # Fetch augments
        cursor.execute("""
            SELECT GROUP_CONCAT(a.name, ', ') FROM Augments a
            JOIN Player_Augments pa ON a.augmentID = pa.augmentID
            WHERE pa.playerID = ?
        """, (playerID,))
        augments = cursor.fetchone()[0] or ''
        # Fetch champions
        cursor.execute("""
            SELECT GROUP_CONCAT(ch.name, ', ') FROM Champions ch
            JOIN Player_Champions pch ON ch.championID = pch.championID
            WHERE pch.playerID = ?
        """, (playerID,))
        champions = cursor.fetchone()[0] or ''
        tree.insert('', tk.END, values=(name, winrate, components, augments, champions, teamName))
    conn.close()

# Function to populate team composition list
def populate_team_list(tree):
    for item in tree.get_children():
        tree.delete(item)
    conn = connect_db()
    cursor = conn.cursor()
    cursor.execute("SELECT teamName, winrate FROM Team_Composition")
    teams = cursor.fetchall()
    for team in teams:
        teamName, winrate = team
        # Fetch champions
        cursor.execute("""
            SELECT GROUP_CONCAT(ch.name, ', ')
            FROM Champions ch
            JOIN Team_Composition_Champions tcc ON ch.championID = tcc.championID
            WHERE tcc.teamName = ?
        """, (teamName,))
        champions = cursor.fetchone()[0] or ''
        tree.insert('', tk.END, values=(teamName, winrate, champions))
    conn.close()

def get_all_effects():
    conn = connect_db()
    cursor = conn.cursor()
    cursor.execute("SELECT effectID, effect_name FROM Effects")
    effects = cursor.fetchall()
    conn.close()
    return effects

def get_all_origins():
    conn = connect_db()
    cursor = conn.cursor()
    cursor.execute("SELECT originID, name FROM Origins")
    origins = cursor.fetchall()
    conn.close()
    return origins

def get_all_classes():
    conn = connect_db()
    cursor = conn.cursor()
    cursor.execute("SELECT classID, name FROM Classes")
    classes = cursor.fetchall()
    conn.close()
    return classes

def get_all_roles():
    conn = connect_db()
    cursor = conn.cursor()
    cursor.execute("SELECT roleID, role_name FROM Roles")
    roles = cursor.fetchall()
    conn.close()
    return roles

# def filter_champions_by_effect():
    # Clear existing items in the treeview
    for item in result_tree.get_children():
        result_tree.delete(item)
    
    selected_effect_ids = [effect_id for effect_id, var in effect_vars.items() if var.get() == 1]
    
    if not selected_effect_ids:
        messagebox.showwarning("No Effects Selected", "Please select at least one effect to filter.")
        return
    
    match_type = match_type_var.get()
    
    # Build the query
    conn = connect_db()
    cursor = conn.cursor()
    
    placeholders = ','.join(['?'] * len(selected_effect_ids))
    
    if match_type == 'Any':
        # Fetch champions that have any of the selected effects
        query = f"""
        SELECT DISTINCT c.championID, c.name
        FROM Champions c
        JOIN Champion_Effects ce ON c.championID = ce.championID
        WHERE ce.effectID IN ({placeholders})
        """
        cursor.execute(query, selected_effect_ids)
    else:
        # Fetch champions that have all of the selected effects
        num_selected_effects = len(selected_effect_ids)
        query = f"""
        SELECT c.championID, c.name
        FROM Champions c
        JOIN Champion_Effects ce ON c.championID = ce.championID
        WHERE ce.effectID IN ({placeholders})
        GROUP BY c.championID
        HAVING COUNT(DISTINCT ce.effectID) = ?
        """
        cursor.execute(query, selected_effect_ids + [num_selected_effects])
    
    champions = cursor.fetchall()
    
    if not champions:
        messagebox.showinfo("No Champions Found", "No champions have the selected effects.")
        return
    
    # Now, for each champion, fetch their origins, classes, effects, roles
    for champ_id, champ_name in champions:
        # Fetch origins
        cursor.execute("""
            SELECT GROUP_CONCAT(o.name, ', ')
            FROM Origins o
            JOIN Champion_Origins co ON o.originID = co.originID
            WHERE co.championID = ?
        """, (champ_id,))
        origins = cursor.fetchone()[0] or ''
        
        # Fetch classes
        cursor.execute("""
            SELECT GROUP_CONCAT(cl.name, ', ')
            FROM Classes cl
            JOIN Champion_Classes cc ON cl.classID = cc.classID
            WHERE cc.championID = ?
        """, (champ_id,))
        classes = cursor.fetchone()[0] or ''
        
        # Fetch effects
        cursor.execute("""
            SELECT GROUP_CONCAT(e.effect_name, ', ')
            FROM Effects e
            JOIN Champion_Effects ce ON e.effectID = ce.effectID
            WHERE ce.championID = ?
        """, (champ_id,))
        effects = cursor.fetchone()[0] or ''
        
        # Fetch roles
        cursor.execute("""
            SELECT GROUP_CONCAT(r.role_name, ', ')
            FROM Roles r
            JOIN Champion_Roles cr ON r.roleID = cr.roleID
            WHERE cr.championID = ?
        """, (champ_id,))
        roles = cursor.fetchone()[0] or ''
        
        # Insert into treeview
        result_tree.insert('', tk.END, values=(champ_name, origins, classes, effects, roles))
    conn.close()

# def filter_champions_by_effect():
    # Clear existing items in the treeview
    for item in result_tree.get_children():
        result_tree.delete(item)
    
    # Get selected effects from the Listbox
    selected_indices = effects_listbox.curselection()
    if not selected_indices:
        messagebox.showwarning("No Effects Selected", "Please select at least one effect to filter.")
        return
    selected_effect_ids = [displayed_effects[i][0] for i in selected_indices]
    
    # Get match type
    match_type = match_type_var.get()
    
    # Build the query
    conn = connect_db()
    cursor = conn.cursor()
    
    placeholders = ','.join(['?'] * len(selected_effect_ids))
    
    if match_type == 'Any':
        # Fetch champions that have any of the selected effects
        query = f"""
        SELECT DISTINCT c.championID, c.name
        FROM Champions c
        JOIN Champion_Effects ce ON c.championID = ce.championID
        WHERE ce.effectID IN ({placeholders})
        """
        cursor.execute(query, selected_effect_ids)
    else:
        # Fetch champions that have all of the selected effects
        num_selected_effects = len(selected_effect_ids)
        query = f"""
        SELECT c.championID, c.name
        FROM Champions c
        JOIN Champion_Effects ce ON c.championID = ce.championID
        WHERE ce.effectID IN ({placeholders})
        GROUP BY c.championID
        HAVING COUNT(DISTINCT ce.effectID) = {num_selected_effects}
        """
        cursor.execute(query, selected_effect_ids)
    
    champions = cursor.fetchall()
    
    if not champions:
        messagebox.showinfo("No Champions Found", "No champions have the selected effects.")
        conn.close()
        return
    
    # Now, for each champion, fetch their origins, classes, effects, roles
    for champ_id, champ_name in champions:
        # Fetch origins
        cursor.execute("""
            SELECT GROUP_CONCAT(o.name, ', ')
            FROM Origins o
            JOIN Champion_Origins co ON o.originID = co.originID
            WHERE co.championID = ?
        """, (champ_id,))
        origins = cursor.fetchone()[0] or ''
        
        # Fetch classes
        cursor.execute("""
            SELECT GROUP_CONCAT(cl.name, ', ')
            FROM Classes cl
            JOIN Champion_Classes cc ON cl.classID = cc.classID
            WHERE cc.championID = ?
        """, (champ_id,))
        classes = cursor.fetchone()[0] or ''
        
        # Fetch effects
        cursor.execute("""
            SELECT GROUP_CONCAT(e.effect_name, ', ')
            FROM Effects e
            JOIN Champion_Effects ce ON e.effectID = ce.effectID
            WHERE ce.championID = ?
        """, (champ_id,))
        effects = cursor.fetchone()[0] or ''
        
        # Fetch roles
        cursor.execute("""
            SELECT GROUP_CONCAT(r.role_name, ', ')
            FROM Roles r
            JOIN Champion_Roles cr ON r.roleID = cr.roleID
            WHERE cr.championID = ?
        """, (champ_id,))
        roles = cursor.fetchone()[0] or ''
        
        # Insert into treeview
        result_tree.insert('', tk.END, values=(champ_name, origins, classes, effects, roles))
    conn.close()

# def filter_champions():


    # Clear existing items in the treeview
    for item in result_tree.get_children():
        result_tree.delete(item)
    
    # Get selected effects from the Listbox
    selected_effect_indices = effects_listbox.curselection()
    selected_origin_indices = origins_listbox.curselection()
    selected_class_indices = classes_listbox.curselection()
    selected_role_indices = roles_listbox.curselection()
    
    # At least one filter must be selected
    if not (selected_effect_indices or selected_origin_indices or selected_class_indices or selected_role_indices):
        messagebox.showwarning("No Filters Selected", "Please select at least one filter to search.")
        return
    
    conn = connect_db()
    cursor = conn.cursor()
    
    # Base query
    query = "SELECT DISTINCT c.championID, c.name FROM Champions c"
    joins = []
    wheres = []
    params = []
    
    # Handle Effects
    if selected_effect_indices:
        selected_effect_ids = [displayed_effects[i][0] for i in selected_effect_indices]
        placeholders = ','.join(['?'] * len(selected_effect_ids))
        joins.append("JOIN Champion_Effects ce ON c.championID = ce.championID")
        wheres.append(f"ce.effectID IN ({placeholders})")
        params.extend(selected_effect_ids)
    
    # Handle Origins
    if selected_origin_indices:
        selected_origin_ids = [displayed_origins[i][0] for i in selected_origin_indices]
        placeholders = ','.join(['?'] * len(selected_origin_ids))
        joins.append("JOIN Champion_Origins co ON c.championID = co.championID")
        wheres.append(f"co.originID IN ({placeholders})")
        params.extend(selected_origin_ids)
    
    # Handle Classes
    if selected_class_indices:
        selected_class_ids = [displayed_classes[i][0] for i in selected_class_indices]
        placeholders = ','.join(['?'] * len(selected_class_ids))
        joins.append("JOIN Champion_Classes cc ON c.championID = cc.championID")
        wheres.append(f"cc.classID IN ({placeholders})")
        params.extend(selected_class_ids)
    
    # Handle Roles
    if selected_role_indices:
        selected_role_ids = [displayed_roles[i][0] for i in selected_role_indices]
        placeholders = ','.join(['?'] * len(selected_role_ids))
        joins.append("JOIN Champion_Roles cr ON c.championID = cr.championID")
        wheres.append(f"cr.roleID IN ({placeholders})")
        params.extend(selected_role_ids)
    
    # Combine the query
    if joins:
        query += ' ' + ' '.join(joins)
    if wheres:
        query += ' WHERE ' + ' AND '.join(wheres)
    
    # Execute the query
    cursor.execute(query, params)
    champions = cursor.fetchall()
    
    if not champions:
        messagebox.showinfo("No Champions Found", "No champions match the selected filters.")
        conn.close()
        return
    
    # Now, for each champion, fetch their origins, classes, effects, roles
    for champ_id, champ_name in champions:
        # Fetch origins
        cursor.execute("""
            SELECT GROUP_CONCAT(o.name, ', ')
            FROM Origins o
            JOIN Champion_Origins co ON o.originID = co.originID
            WHERE co.championID = ?
        """, (champ_id,))
        origins = cursor.fetchone()[0] or ''
        
        # Fetch classes
        cursor.execute("""
            SELECT GROUP_CONCAT(cl.name, ', ')
            FROM Classes cl
            JOIN Champion_Classes cc ON cl.classID = cc.classID
            WHERE cc.championID = ?
        """, (champ_id,))
        classes = cursor.fetchone()[0] or ''
        
        # Fetch effects
        cursor.execute("""
            SELECT GROUP_CONCAT(e.effect_name, ', ')
            FROM Effects e
            JOIN Champion_Effects ce ON e.effectID = ce.effectID
            WHERE ce.championID = ?
        """, (champ_id,))
        effects = cursor.fetchone()[0] or ''
        
        # Fetch roles
        cursor.execute("""
            SELECT GROUP_CONCAT(r.role_name, ', ')
            FROM Roles r
            JOIN Champion_Roles cr ON r.roleID = cr.roleID
            WHERE cr.championID = ?
        """, (champ_id,))
        roles = cursor.fetchone()[0] or ''
        
        # Insert into treeview
        result_tree.insert('', tk.END, values=(champ_name, origins, classes, effects, roles))
    conn.close()

# def filter_champions():
    # Clear existing items in the treeview
    for item in result_tree.get_children():
        result_tree.delete(item)
    
    # Get selected indices and match types
    selected_effect_indices = effects_listbox.curselection()
    selected_origin_indices = origins_listbox.curselection()
    selected_class_indices = classes_listbox.curselection()
    selected_role_indices = roles_listbox.curselection()
    
    # At least one filter must be selected
    if not (selected_effect_indices or selected_origin_indices or selected_class_indices or selected_role_indices):
        messagebox.showwarning("No Filters Selected", "Please select at least one filter to search.")
        return
    
    conn = connect_db()
    cursor = conn.cursor()
    
    # Base query
    query = "SELECT c.championID, c.name FROM Champions c"
    joins = []
    wheres = []
    havings = []
    group_by = " GROUP BY c.championID"
    params = []
    
    # Handle Effects
    if selected_effect_indices:
        selected_effect_ids = [displayed_effects[i][0] for i in selected_effect_indices]
        placeholders = ','.join(['?'] * len(selected_effect_ids))
        joins.append("JOIN Champion_Effects ce ON c.championID = ce.championID")
        effect_match_type = effect_match_type_var.get()
        if effect_match_type == 'Any':
            wheres.append(f"ce.effectID IN ({placeholders})")
            params.extend(selected_effect_ids)
        else:
            # 'All' match type
            wheres.append(f"ce.effectID IN ({placeholders})")
            params.extend(selected_effect_ids)
            havings.append(f"COUNT(DISTINCT ce.effectID) = {len(selected_effect_ids)}")
    
    # Handle Origins
    if selected_origin_indices:
        selected_origin_ids = [displayed_origins[i][0] for i in selected_origin_indices]
        placeholders = ','.join(['?'] * len(selected_origin_ids))
        joins.append("JOIN Champion_Origins co ON c.championID = co.championID")
        origin_match_type = origin_match_type_var.get()
        if origin_match_type == 'Any':
            wheres.append(f"co.originID IN ({placeholders})")
            params.extend(selected_origin_ids)
        else:
            wheres.append(f"co.originID IN ({placeholders})")
            params.extend(selected_origin_ids)
            havings.append(f"COUNT(DISTINCT co.originID) = {len(selected_origin_ids)}")
    
    # Handle Classes
    if selected_class_indices:
        selected_class_ids = [displayed_classes[i][0] for i in selected_class_indices]
        placeholders = ','.join(['?'] * len(selected_class_ids))
        joins.append("JOIN Champion_Classes cc ON c.championID = cc.championID")
        class_match_type = class_match_type_var.get()
        if class_match_type == 'Any':
            wheres.append(f"cc.classID IN ({placeholders})")
            params.extend(selected_class_ids)
        else:
            wheres.append(f"cc.classID IN ({placeholders})")
            params.extend(selected_class_ids)
            havings.append(f"COUNT(DISTINCT cc.classID) = {len(selected_class_ids)}")
    
    # Handle Roles
    if selected_role_indices:
        selected_role_ids = [displayed_roles[i][0] for i in selected_role_indices]
        placeholders = ','.join(['?'] * len(selected_role_ids))
        joins.append("JOIN Champion_Roles cr ON c.championID = cr.championID")
        role_match_type = role_match_type_var.get()
        if role_match_type == 'Any':
            wheres.append(f"cr.roleID IN ({placeholders})")
            params.extend(selected_role_ids)
        else:
            wheres.append(f"cr.roleID IN ({placeholders})")
            params.extend(selected_role_ids)
            havings.append(f"COUNT(DISTINCT cr.roleID) = {len(selected_role_ids)}")
    
    # Combine the query
    if joins:
        query += ' ' + ' '.join(joins)
    if wheres:
        query += ' WHERE ' + ' AND '.join(wheres)
    
    # Add GROUP BY and HAVING clauses if needed
    if havings:
        query += group_by
        query += ' HAVING ' + ' AND '.join(havings)
    
    # Debugging: print the query and params
    # print("Query:", query)
    # print("Params:", params)
    
    # Execute the query
    cursor.execute(query, params)
    champions = cursor.fetchall()
    
    if not champions:
        messagebox.showinfo("No Champions Found", "No champions match the selected filters.")
        conn.close()
        return
    
    # Now, for each champion, fetch their origins, classes, effects, roles
    for champ_id, champ_name in champions:
        # Fetch origins
        cursor.execute("""
            SELECT GROUP_CONCAT(o.name, ', ')
            FROM Origins o
            JOIN Champion_Origins co ON o.originID = co.originID
            WHERE co.championID = ?
        """, (champ_id,))
        origins = cursor.fetchone()[0] or ''
        
        # Fetch classes
        cursor.execute("""
            SELECT GROUP_CONCAT(cl.name, ', ')
            FROM Classes cl
            JOIN Champion_Classes cc ON cl.classID = cc.classID
            WHERE cc.championID = ?
        """, (champ_id,))
        classes = cursor.fetchone()[0] or ''
        
        # Fetch effects
        cursor.execute("""
            SELECT GROUP_CONCAT(e.effect_name, ', ')
            FROM Effects e
            JOIN Champion_Effects ce ON e.effectID = ce.effectID
            WHERE ce.championID = ?
        """, (champ_id,))
        effects = cursor.fetchone()[0] or ''
        
        # Fetch roles
        cursor.execute("""
            SELECT GROUP_CONCAT(r.role_name, ', ')
            FROM Roles r
            JOIN Champion_Roles cr ON r.roleID = cr.roleID
            WHERE cr.championID = ?
        """, (champ_id,))
        roles = cursor.fetchone()[0] or ''
        
        # Insert into treeview
        result_tree.insert('', tk.END, values=(champ_name, origins, classes, effects, roles))
    conn.close()

# def filter_champions():
    # Clear existing items in the treeview
    for item in result_tree.get_children():
        result_tree.delete(item)
    
    # Clear the filters display
    filters_display.configure(state='normal')
    filters_display.delete('1.0', tk.END)
    
    # Get selected indices and match types
    selected_effect_indices = effects_listbox.curselection()
    selected_origin_indices = origins_listbox.curselection()
    selected_class_indices = classes_listbox.curselection()
    selected_role_indices = roles_listbox.curselection()
    
    # At least one filter must be selected
    if not (selected_effect_indices or selected_origin_indices or selected_class_indices or selected_role_indices):
        messagebox.showwarning("No Filters Selected", "Please select at least one filter to search.")
        return
    
    conn = connect_db()
    cursor = conn.cursor()
    
    # Base query
    query = "SELECT c.championID, c.name FROM Champions c"
    joins = []
    wheres = []
    havings = []
    group_by_needed = False
    params = []
    
    # Prepare filters summary
    filters_summary = ""
    
    # Handle Effects
    if selected_effect_indices:
        selected_effect_ids = [displayed_effects[i][0] for i in selected_effect_indices]
        selected_effect_names = [displayed_effects[i][1] for i in selected_effect_indices]
        placeholders = ','.join(['?'] * len(selected_effect_ids))
        joins.append("JOIN Champion_Effects ce ON c.championID = ce.championID")
        effect_match_type = effect_match_type_var.get()
        if effect_match_type == 'Any':
            wheres.append(f"ce.effectID IN ({placeholders})")
            params.extend(selected_effect_ids)
        else:
            # 'All' match type
            wheres.append(f"ce.effectID IN ({placeholders})")
            params.extend(selected_effect_ids)
            havings.append(f"COUNT(DISTINCT ce.effectID) = {len(selected_effect_ids)}")
            group_by_needed = True
        # Add to filters summary
        filters_summary += f"Effects ({effect_match_type}): {', '.join(selected_effect_names)}\n"
    
    # Handle Origins
    if selected_origin_indices:
        selected_origin_ids = [displayed_origins[i][0] for i in selected_origin_indices]
        selected_origin_names = [displayed_origins[i][1] for i in selected_origin_indices]
        placeholders = ','.join(['?'] * len(selected_origin_ids))
        joins.append("JOIN Champion_Origins co ON c.championID = co.championID")
        origin_match_type = origin_match_type_var.get()
        if origin_match_type == 'Any':
            wheres.append(f"co.originID IN ({placeholders})")
            params.extend(selected_origin_ids)
        else:
            wheres.append(f"co.originID IN ({placeholders})")
            params.extend(selected_origin_ids)
            havings.append(f"COUNT(DISTINCT co.originID) = {len(selected_origin_ids)}")
            group_by_needed = True
        # Add to filters summary
        filters_summary += f"Origins ({origin_match_type}): {', '.join(selected_origin_names)}\n"
    
    # Handle Classes
    if selected_class_indices:
        selected_class_ids = [displayed_classes[i][0] for i in selected_class_indices]
        selected_class_names = [displayed_classes[i][1] for i in selected_class_indices]
        placeholders = ','.join(['?'] * len(selected_class_ids))
        joins.append("JOIN Champion_Classes cc ON c.championID = cc.championID")
        class_match_type = class_match_type_var.get()
        if class_match_type == 'Any':
            wheres.append(f"cc.classID IN ({placeholders})")
            params.extend(selected_class_ids)
        else:
            wheres.append(f"cc.classID IN ({placeholders})")
            params.extend(selected_class_ids)
            havings.append(f"COUNT(DISTINCT cc.classID) = {len(selected_class_ids)}")
            group_by_needed = True
        # Add to filters summary
        filters_summary += f"Classes ({class_match_type}): {', '.join(selected_class_names)}\n"
    
    # Handle Roles
    if selected_role_indices:
        selected_role_ids = [displayed_roles[i][0] for i in selected_role_indices]
        selected_role_names = [displayed_roles[i][1] for i in selected_role_indices]
        placeholders = ','.join(['?'] * len(selected_role_ids))
        joins.append("JOIN Champion_Roles cr ON c.championID = cr.championID")
        role_match_type = role_match_type_var.get()
        if role_match_type == 'Any':
            wheres.append(f"cr.roleID IN ({placeholders})")
            params.extend(selected_role_ids)
        else:
            wheres.append(f"cr.roleID IN ({placeholders})")
            params.extend(selected_role_ids)
            havings.append(f"COUNT(DISTINCT cr.roleID) = {len(selected_role_ids)}")
            group_by_needed = True
        # Add to filters summary
        filters_summary += f"Roles ({role_match_type}): {', '.join(selected_role_names)}\n"
    
    # Combine the query
    if joins:
        query += ' ' + ' '.join(joins)
    if wheres:
        query += ' WHERE ' + ' AND '.join(wheres)
    if group_by_needed or havings:
        query += ' GROUP BY c.championID'
        if havings:
            query += ' HAVING ' + ' AND '.join(havings)
    
    # Execute the query
    cursor.execute(query, params)
    champions = cursor.fetchall()
    
    if not champions:
        messagebox.showinfo("No Champions Found", "No champions match the selected filters.")
        conn.close()
        return
    
    # Display the filters applied
    filters_display.insert(tk.END, filters_summary)
    filters_display.configure(state='disabled')
    
    # Now, for each champion, fetch their origins, classes, effects, roles
    for champ_id, champ_name in champions:
        # Fetch origins
        cursor.execute("""
            SELECT GROUP_CONCAT(o.name, ', ')
            FROM Origins o
            JOIN Champion_Origins co ON o.originID = co.originID
            WHERE co.championID = ?
        """, (champ_id,))
        origins = cursor.fetchone()[0] or ''
        
        # Fetch classes
        cursor.execute("""
            SELECT GROUP_CONCAT(cl.name, ', ')
            FROM Classes cl
            JOIN Champion_Classes cc ON cl.classID = cc.classID
            WHERE cc.championID = ?
        """, (champ_id,))
        classes = cursor.fetchone()[0] or ''
        
        # Fetch effects
        cursor.execute("""
            SELECT GROUP_CONCAT(e.effect_name, ', ')
            FROM Effects e
            JOIN Champion_Effects ce ON e.effectID = ce.effectID
            WHERE ce.championID = ?
        """, (champ_id,))
        effects = cursor.fetchone()[0] or ''
        
        # Fetch roles
        cursor.execute("""
            SELECT GROUP_CONCAT(r.role_name, ', ')
            FROM Roles r
            JOIN Champion_Roles cr ON r.roleID = cr.roleID
            WHERE cr.championID = ?
        """, (champ_id,))
        roles = cursor.fetchone()[0] or ''
        
        # Insert into treeview
        result_tree.insert('', tk.END, values=(champ_name, origins, classes, effects, roles))
    conn.close()

# Function to create filter frames
def create_filter_frame(parent, title, search_var):
    frame = ttk.LabelFrame(parent, text=title)
    frame.pack(fill='both', expand=True, padx=5, pady=5)

    # Search Entry
    search_label = ttk.Label(frame, text=f"Search {title}:")
    search_label.pack(pady=5)
    search_entry = ttk.Entry(frame, textvariable=search_var)
    search_entry.pack(pady=5)

    # Scrollable Frame for Checkbuttons
    canvas = tk.Canvas(frame)
    scrollbar = ttk.Scrollbar(frame, orient='vertical', command=canvas.yview)
    scrollable_frame = ttk.Frame(canvas)

    scrollable_frame.bind(
        "<Configure>",
        lambda e: canvas.configure(
            scrollregion=canvas.bbox("all")
        )
    )

    canvas.create_window((0, 0), window=scrollable_frame, anchor='nw')
    canvas.configure(yscrollcommand=scrollbar.set)

    canvas.pack(side='left', fill='both', expand=True)
    scrollbar.pack(side='right', fill='y')

    return frame, scrollable_frame

# Function to set up checkbuttons
def setup_checkbuttons(scrollable_frame, items, vars_dict, displayed_items):
    for item_id, item_name in items:
        var = tk.BooleanVar()
        chk = ttk.Checkbutton(scrollable_frame, text=item_name, variable=var)
        chk.pack(anchor='w')
        vars_dict[item_id] = var
        displayed_items[item_id] = (item_name, chk)

# Function to update checkbuttons based on search
def update_checkbuttons(search_var, displayed_items):
    search_text = search_var.get().lower()
    for item_id, (item_name, chk) in displayed_items.items():
        if search_text in item_name.lower():
            chk.pack(anchor='w')
        else:
            chk.pack_forget()

# Function to filter champions
def filter_champions():
    # Clear existing items in the treeview
    for item in result_tree.get_children():
        result_tree.delete(item)

    # Clear the filters display
    filters_display.configure(state='normal')
    filters_display.delete('1.0', tk.END)

    # Collect selected options
    selected_effect_ids = [effect_id for effect_id, var in effect_vars.items() if var.get()]
    selected_origin_ids = [origin_id for origin_id, var in origin_vars.items() if var.get()]
    selected_class_ids = [class_id for class_id, var in class_vars.items() if var.get()]
    selected_role_ids = [role_id for role_id, var in role_vars.items() if var.get()]

    # At least one filter must be selected
    if not (selected_effect_ids or selected_origin_ids or selected_class_ids or selected_role_ids):
        messagebox.showwarning("No Filters Selected", "Please select at least one filter to search.")
        return

    conn = connect_db()
    cursor = conn.cursor()

    # Base query
    query = "SELECT c.championID, c.name FROM Champions c"
    joins = []
    wheres = []
    params = []
    group_by_needed = False
    havings = []

    # Prepare filters summary
    filters_summary = ""

    # Handle Effects
    if selected_effect_ids:
        placeholders = ','.join(['?'] * len(selected_effect_ids))
        joins.append("JOIN Champion_Effects ce ON c.championID = ce.championID")
        effect_match_type = effect_match_type_var.get()
        if effect_match_type == 'Any':
            wheres.append(f"ce.effectID IN ({placeholders})")
            params.extend(selected_effect_ids)
        else:
            wheres.append(f"ce.effectID IN ({placeholders})")
            params.extend(selected_effect_ids)
            havings.append(f"COUNT(DISTINCT ce.effectID) = {len(selected_effect_ids)}")
            group_by_needed = True
        effect_names = [displayed_effects[effect_id][0] for effect_id in selected_effect_ids]
        filters_summary += f"Effects ({effect_match_type}): {', '.join(effect_names)}\n"

    # Handle Origins
    if selected_origin_ids:
        placeholders = ','.join(['?'] * len(selected_origin_ids))
        joins.append("JOIN Champion_Origins co ON c.championID = co.championID")
        origin_match_type = origin_match_type_var.get()
        if origin_match_type == 'Any':
            wheres.append(f"co.originID IN ({placeholders})")
            params.extend(selected_origin_ids)
        else:
            wheres.append(f"co.originID IN ({placeholders})")
            params.extend(selected_origin_ids)
            havings.append(f"COUNT(DISTINCT co.originID) = {len(selected_origin_ids)}")
            group_by_needed = True
        origin_names = [displayed_origins[origin_id][0] for origin_id in selected_origin_ids]
        filters_summary += f"Origins ({origin_match_type}): {', '.join(origin_names)}\n"

    # Handle Classes
    if selected_class_ids:
        placeholders = ','.join(['?'] * len(selected_class_ids))
        joins.append("JOIN Champion_Classes cc ON c.championID = cc.championID")
        class_match_type = class_match_type_var.get()
        if class_match_type == 'Any':
            wheres.append(f"cc.classID IN ({placeholders})")
            params.extend(selected_class_ids)
        else:
            wheres.append(f"cc.classID IN ({placeholders})")
            params.extend(selected_class_ids)
            havings.append(f"COUNT(DISTINCT cc.classID) = {len(selected_class_ids)}")
            group_by_needed = True
        class_names = [displayed_classes[class_id][0] for class_id in selected_class_ids]
        filters_summary += f"Classes ({class_match_type}): {', '.join(class_names)}\n"

    # Handle Roles
    if selected_role_ids:
        placeholders = ','.join(['?'] * len(selected_role_ids))
        joins.append("JOIN Champion_Roles cr ON c.championID = cr.championID")
        role_match_type = role_match_type_var.get()
        if role_match_type == 'Any':
            wheres.append(f"cr.roleID IN ({placeholders})")
            params.extend(selected_role_ids)
        else:
            wheres.append(f"cr.roleID IN ({placeholders})")
            params.extend(selected_role_ids)
            havings.append(f"COUNT(DISTINCT cr.roleID) = {len(selected_role_ids)}")
            group_by_needed = True
        role_names = [displayed_roles[role_id][0] for role_id in selected_role_ids]
        filters_summary += f"Roles ({role_match_type}): {', '.join(role_names)}\n"

    # Combine the query
    if joins:
        query += ' ' + ' '.join(joins)
    if wheres:
        query += ' WHERE ' + ' AND '.join(wheres)
    if group_by_needed or havings:
        query += ' GROUP BY c.championID'
        if havings:
            query += ' HAVING ' + ' AND '.join(havings)

    # Execute the query
    cursor.execute(query, params)
    champions = cursor.fetchall()

    if not champions:
        messagebox.showinfo("No Champions Found", "No champions match the selected filters.")
        conn.close()
        return

    # Display the filters applied
    filters_display.insert(tk.END, filters_summary)
    filters_display.configure(state='disabled')

    # Now, for each champion, fetch their origins, classes, effects, roles
    for champ_id, champ_name in champions:
        # Fetch origins
        cursor.execute("""
            SELECT GROUP_CONCAT(o.name, ', ')
            FROM Origins o
            JOIN Champion_Origins co ON o.originID = co.originID
            WHERE co.championID = ?
        """, (champ_id,))
        origins = cursor.fetchone()[0] or ''

        # Fetch classes
        cursor.execute("""
            SELECT GROUP_CONCAT(cl.name, ', ')
            FROM Classes cl
            JOIN Champion_Classes cc ON cl.classID = cc.classID
            WHERE cc.championID = ?
        """, (champ_id,))
        classes = cursor.fetchone()[0] or ''

        # Fetch effects
        cursor.execute("""
            SELECT GROUP_CONCAT(e.effect_name, ', ')
            FROM Effects e
            JOIN Champion_Effects ce ON e.effectID = ce.effectID
            WHERE ce.championID = ?
        """, (champ_id,))
        effects = cursor.fetchone()[0] or ''

        # Fetch roles
        cursor.execute("""
            SELECT GROUP_CONCAT(r.role_name, ', ')
            FROM Roles r
            JOIN Champion_Roles cr ON r.roleID = cr.roleID
            WHERE cr.championID = ?
        """, (champ_id,))
        roles = cursor.fetchone()[0] or ''

        # Insert into treeview
        result_tree.insert('', tk.END, values=(champ_name, origins, classes, effects, roles))
    conn.close()


# Function to clear filters
def clear_filters():
    # Clear selections in Effects
    for var in effect_vars.values():
        var.set(False)
    effect_search_var.set('')
    effect_match_type_var.set('Any')

    # Clear selections in Origins
    for var in origin_vars.values():
        var.set(False)
    origin_search_var.set('')
    origin_match_type_var.set('Any')

    # Clear selections in Classes
    for var in class_vars.values():
        var.set(False)
    class_search_var.set('')
    class_match_type_var.set('Any')

    # Clear selections in Roles
    for var in role_vars.values():
        var.set(False)
    role_search_var.set('')
    role_match_type_var.set('Any')

    # Clear the result treeview
    for item in result_tree.get_children():
        result_tree.delete(item)

    # Clear the filters display
    filters_display.configure(state='normal')
    filters_display.delete('1.0', tk.END)
    filters_display.configure(state='disabled')

# Function to refresh tabs when selected
def on_tab_selected(event):
    selected_tab = event.widget.tab('current')['text']
    if selected_tab == 'Champions':
        populate_champion_list(champion_tree)
    elif selected_tab == 'Players':
        populate_player_list(player_tree)
    elif selected_tab == 'Teams':
        populate_team_list(team_tree)
    elif selected_tab == 'Champion Filter':
        pass

# Function to add a new player
def add_player():
    name = player_name_entry.get()
    health = player_health_entry.get()
    podium_position = player_podium_entry.get()
    winrate = player_winrate_entry.get()
    team_name = player_team_entry.get()
    if not name:
        messagebox.showwarning("Input Error", "Player name is required.")
        return
    conn = connect_db()
    cursor = conn.cursor()
    cursor.execute("""
        INSERT INTO Player (name, health, podium_position, winrate, teamName)
        VALUES (?, ?, ?, ?, ?)
    """, (name, health or None, podium_position or None, winrate or None, team_name or None))
    conn.commit()
    conn.close()
    messagebox.showinfo("Success", f"Player '{name}' added successfully.")
    # Clear the entries
    player_name_entry.delete(0, tk.END)
    player_health_entry.delete(0, tk.END)
    player_podium_entry.delete(0, tk.END)
    player_winrate_entry.delete(0, tk.END)
    player_team_entry.delete(0, tk.END)

# Function to assign augment to player
def assign_augment_to_player():
    player_id = augment_player_id_entry.get()
    augment_id = augment_id_entry.get()
    if not player_id or not augment_id:
        messagebox.showwarning("Input Error", "Both Player ID and Augment ID are required.")
        return
    conn = connect_db()
    cursor = conn.cursor()
    # Verify player exists
    cursor.execute("SELECT name FROM Player WHERE playerID = ?", (player_id,))
    player = cursor.fetchone()
    if not player:
        messagebox.showerror("Error", "Player not found.")
        conn.close()
        return
    # Verify augment exists
    cursor.execute("SELECT name FROM Augments WHERE augmentID = ?", (augment_id,))
    augment = cursor.fetchone()
    if not augment:
        messagebox.showerror("Error", "Augment not found.")
        conn.close()
        return
    # Assign augment to player
    cursor.execute("""
        INSERT OR IGNORE INTO Player_Augments (playerID, augmentID)
        VALUES (?, ?)
    """, (player_id, augment_id))
    conn.commit()
    conn.close()
    messagebox.showinfo("Success", f"Augment '{augment[0]}' assigned to player '{player[0]}'.")
    # Clear entries
    augment_player_id_entry.delete(0, tk.END)
    augment_id_entry.delete(0, tk.END)

# Function to assign component to player
def assign_component_to_player():
    player_id = component_player_id_entry.get()
    component_id = component_id_entry.get()
    if not player_id or not component_id:
        messagebox.showwarning("Input Error", "Both Player ID and Component ID are required.")
        return
    conn = connect_db()
    cursor = conn.cursor()
    # Verify player exists
    cursor.execute("SELECT name FROM Player WHERE playerID = ?", (player_id,))
    player = cursor.fetchone()
    if not player:
        messagebox.showerror("Error", "Player not found.")
        conn.close()
        return
    # Verify component exists
    cursor.execute("SELECT name FROM Components WHERE componentID = ?", (component_id,))
    component = cursor.fetchone()
    if not component:
        messagebox.showerror("Error", "Component not found.")
        conn.close()
        return
    # Assign component to player
    cursor.execute("""
        INSERT OR IGNORE INTO Player_components (playerID, componentID)
        VALUES (?, ?)
    """, (player_id, component_id))
    conn.commit()
    conn.close()
    messagebox.showinfo("Success", f"Component '{component[0]}' assigned to player '{player[0]}'.")
    # Clear entries
    component_player_id_entry.delete(0, tk.END)
    component_id_entry.delete(0, tk.END)

# Function to add a team composition
def add_team_composition():
    team_name = team_name_entry.get().strip()
    winrate = team_winrate_entry.get().strip()
    
    if not team_name:
        messagebox.showwarning("Input Error", "Team name is required.")
        return
    
    conn = connect_db()
    cursor = conn.cursor()
    
    # Check if team composition already exists
    cursor.execute("SELECT teamName FROM Team_Composition WHERE teamName = ?", (team_name,))
    if cursor.fetchone():
        messagebox.showerror("Error", f"Team composition '{team_name}' already exists.")
        conn.close()
        return
    
    # Insert into Team_Composition
    cursor.execute("""
        INSERT INTO Team_Composition (teamName, winrate)
        VALUES (?, ?)
    """, (team_name, float(winrate) if winrate else None))
    
    conn.commit()
    conn.close()
    
    messagebox.showinfo("Success", f"Team composition '{team_name}' added successfully.")
    
    # Clear the entries
    team_name_entry.delete(0, tk.END)
    team_winrate_entry.delete(0, tk.END)

# Function to assign champion to player
def assign_champion_to_player():
    player_id = champion_player_id_entry.get()
    champion_id = champion_id_entry.get()
    if not player_id or not champion_id:
        messagebox.showwarning("Input Error", "Both Player ID and Champion ID are required.")
        return
    conn = connect_db()
    cursor = conn.cursor()
    # Verify player exists
    cursor.execute("SELECT name FROM Player WHERE playerID = ?", (player_id,))
    player = cursor.fetchone()
    if not player:
        messagebox.showerror("Error", "Player not found.")
        conn.close()
        return
    # Verify champion exists
    cursor.execute("SELECT name FROM Champions WHERE championID = ?", (champion_id,))
    champion = cursor.fetchone()
    if not champion:
        messagebox.showerror("Error", "Champion not found.")
        conn.close()
        return
    # Assign champion to player
    cursor.execute("""
        INSERT OR IGNORE INTO Player_Champions (playerID, championID)
        VALUES (?, ?)
    """, (player_id, champion_id))
    conn.commit()
    conn.close()
    messagebox.showinfo("Success", f"Champion '{champion[0]}' assigned to player '{player[0]}'.")
    # Clear entries
    champion_player_id_entry.delete(0, tk.END)
    champion_id_entry.delete(0, tk.END)


# Function to assign a champion to a team composition
def assign_champion_to_team():
    team_name = team_comp_name_entry.get().strip()
    champion_id = team_champion_id_entry.get().strip()
    
    if not team_name or not champion_id:
        messagebox.showwarning("Input Error", "Both Team Name and Champion ID are required.")
        return
    
    conn = connect_db()
    cursor = conn.cursor()
    
    # Verify team composition exists
    cursor.execute("SELECT teamName FROM Team_Composition WHERE teamName = ?", (team_name,))
    team = cursor.fetchone()
    if not team:
        messagebox.showerror("Error", f"Team composition '{team_name}' does not exist.")
        conn.close()
        return
    
    # Verify champion exists
    cursor.execute("SELECT name FROM Champions WHERE championID = ?", (champion_id,))
    champion = cursor.fetchone()
    if not champion:
        messagebox.showerror("Error", f"Champion with ID '{champion_id}' does not exist.")
        conn.close()
        return
    
    # Assign champion to team composition
    try:
        cursor.execute("""
            INSERT INTO Team_Composition_Champions (teamName, championID)
            VALUES (?, ?)
        """, (team_name, champion_id))
    except sqlite3.IntegrityError:
        messagebox.showwarning("Warning", f"Champion '{champion[0]}' is already assigned to team '{team_name}'.")
        conn.close()
        return
    
    conn.commit()
    conn.close()
    
    messagebox.showinfo("Success", f"Champion '{champion[0]}' assigned to team '{team_name}'.")
    
    # Clear entries
    team_comp_name_entry.delete(0, tk.END)
    team_champion_id_entry.delete(0, tk.END)

def treeview_sort_column(tree, col, reverse):
    """Sort Treeview column when a header is clicked."""
    # Get all items in the treeview
    l = [(tree.set(k, col), k) for k in tree.get_children('')]
    
    # Try to convert the data to float for numerical sorting
    try:
        l.sort(key=lambda t: float(t[0]) if t[0] else 0, reverse=reverse)
    except ValueError:
        # If conversion fails, sort as strings
        l.sort(key=lambda t: t[0].lower(), reverse=reverse)
    
    # Rearrange items in sorted positions
    for index, (val, k) in enumerate(l):
        tree.move(k, '', index)
    
    # Reverse sort next time
    tree.heading(col, command=lambda: treeview_sort_column(tree, col, not reverse))

# Initialize main window
root = tk.Tk()
root.title("TFT Database GUI")
root.geometry("1200x700")

# Create tabs
notebook = ttk.Notebook(root)
notebook.pack(expand=True, fill='both')
notebook.bind('<<NotebookTabChanged>>', on_tab_selected)

# Tab 1: View Champions with more details
tab1 = ttk.Frame(notebook)
notebook.add(tab1, text='Champions')

champion_columns = ['Name', 'Ability', 'AD', 'AP', 'Attack Speed', 'HP', 'Armor', 'Magic Resist', 'Mana', 'Mana Cost', 'Attack Range', 'Cost']
champion_tree = ttk.Treeview(tab1, columns=champion_columns, show='headings')

for col in champion_columns:
    champion_tree.heading(col, text=col, command=lambda _col=col: treeview_sort_column(champion_tree, _col, False))
    champion_tree.column(col, width=100, anchor='center')

champion_tree.pack(fill=tk.BOTH, expand=True, padx=10, pady=10)

populate_champion_list(champion_tree)

# Tab 2: Manage Data
tab_manage = ttk.Frame(notebook)
notebook.add(tab_manage, text='Manage Data')

# Create a canvas and scrollbar for the manage data tab
canvas = tk.Canvas(tab_manage)
scrollbar = ttk.Scrollbar(tab_manage, orient='vertical', command=canvas.yview)
scrollable_frame = ttk.Frame(canvas)

scrollable_frame.bind(
    "<Configure>",
    lambda e: canvas.configure(
        scrollregion=canvas.bbox("all")
    )
)

canvas.create_window((0, 0), window=scrollable_frame, anchor='nw')
canvas.configure(yscrollcommand=scrollbar.set)

canvas.pack(side="left", fill="both", expand=True)
scrollbar.pack(side="right", fill="y")

# Section: Add Player
ttk.Label(scrollable_frame, text="Add Player", font=('Helvetica', 14, 'bold')).grid(row=0, column=0, columnspan=2, pady=10)
player_name_label = ttk.Label(scrollable_frame, text="Player Name:")
player_name_label.grid(row=1, column=0, padx=5, pady=5, sticky='E')
player_name_entry = ttk.Entry(scrollable_frame)
player_name_entry.grid(row=1, column=1, padx=5, pady=5)

player_health_label = ttk.Label(scrollable_frame, text="Health:")
player_health_label.grid(row=2, column=0, padx=5, pady=5, sticky='E')
player_health_entry = ttk.Entry(scrollable_frame)
player_health_entry.grid(row=2, column=1, padx=5, pady=5)

player_podium_label = ttk.Label(scrollable_frame, text="Podium Position:")
player_podium_label.grid(row=3, column=0, padx=5, pady=5, sticky='E')
player_podium_entry = ttk.Entry(scrollable_frame)
player_podium_entry.grid(row=3, column=1, padx=5, pady=5)

player_winrate_label = ttk.Label(scrollable_frame, text="Winrate:")
player_winrate_label.grid(row=4, column=0, padx=5, pady=5, sticky='E')
player_winrate_entry = ttk.Entry(scrollable_frame)
player_winrate_entry.grid(row=4, column=1, padx=5, pady=5)

player_team_label = ttk.Label(scrollable_frame, text="Team Name:")
player_team_label.grid(row=5, column=0, padx=5, pady=5, sticky='E')
player_team_entry = ttk.Entry(scrollable_frame)
player_team_entry.grid(row=5, column=1, padx=5, pady=5)

add_player_button = ttk.Button(scrollable_frame, text="Add Player", command=add_player)
add_player_button.grid(row=6, column=0, columnspan=2, pady=10)

# Separator
ttk.Separator(scrollable_frame, orient='horizontal').grid(row=7, column=0, columnspan=2, sticky='ew', pady=10)

# Section: Assign Augment to Player
ttk.Label(scrollable_frame, text="Assign Augment to Player", font=('Helvetica', 14, 'bold')).grid(row=8, column=0, columnspan=2, pady=10)
augment_player_id_label = ttk.Label(scrollable_frame, text="Player ID:")
augment_player_id_label.grid(row=9, column=0, padx=5, pady=5, sticky='E')
augment_player_id_entry = ttk.Entry(scrollable_frame)
augment_player_id_entry.grid(row=9, column=1, padx=5, pady=5)

augment_id_label = ttk.Label(scrollable_frame, text="Augment ID:")
augment_id_label.grid(row=10, column=0, padx=5, pady=5, sticky='E')
augment_id_entry = ttk.Entry(scrollable_frame)
augment_id_entry.grid(row=10, column=1, padx=5, pady=5)

assign_augment_button = ttk.Button(scrollable_frame, text="Assign Augment", command=assign_augment_to_player)
assign_augment_button.grid(row=11, column=0, columnspan=2, pady=10)

# Separator
ttk.Separator(scrollable_frame, orient='horizontal').grid(row=12, column=0, columnspan=2, sticky='ew', pady=10)

# Section: Assign Component to Player
ttk.Label(scrollable_frame, text="Assign Component to Player", font=('Helvetica', 14, 'bold')).grid(row=13, column=0, columnspan=2, pady=10)
component_player_id_label = ttk.Label(scrollable_frame, text="Player ID:")
component_player_id_label.grid(row=14, column=0, padx=5, pady=5, sticky='E')
component_player_id_entry = ttk.Entry(scrollable_frame)
component_player_id_entry.grid(row=14, column=1, padx=5, pady=5)

component_id_label = ttk.Label(scrollable_frame, text="Component ID:")
component_id_label.grid(row=15, column=0, padx=5, pady=5, sticky='E')
component_id_entry = ttk.Entry(scrollable_frame)
component_id_entry.grid(row=15, column=1, padx=5, pady=5)

assign_component_button = ttk.Button(scrollable_frame, text="Assign Component", command=assign_component_to_player)
assign_component_button.grid(row=16, column=0, columnspan=2, pady=10)

# Separator
ttk.Separator(scrollable_frame, orient='horizontal').grid(row=17, column=0, columnspan=2, sticky='ew', pady=10)

# Section: Assign Champion to Player
ttk.Label(scrollable_frame, text="Assign Champion to Player", font=('Helvetica', 14, 'bold')).grid(row=18, column=0, columnspan=2, pady=10)
champion_player_id_label = ttk.Label(scrollable_frame, text="Player ID:")
champion_player_id_label.grid(row=19, column=0, padx=5, pady=5, sticky='E')
champion_player_id_entry = ttk.Entry(scrollable_frame)
champion_player_id_entry.grid(row=19, column=1, padx=5, pady=5)

champion_id_label = ttk.Label(scrollable_frame, text="Champion ID:")
champion_id_label.grid(row=20, column=0, padx=5, pady=5, sticky='E')
champion_id_entry = ttk.Entry(scrollable_frame)
champion_id_entry.grid(row=20, column=1, padx=5, pady=5)

assign_champion_button = ttk.Button(scrollable_frame, text="Assign Champion", command=assign_champion_to_player)
assign_champion_button.grid(row=21, column=0, columnspan=2, pady=10)

# Separator
ttk.Separator(scrollable_frame, orient='horizontal').grid(row=22, column=0, columnspan=2, sticky='ew', pady=10)

# Section: Add Team Composition
ttk.Label(scrollable_frame, text="Add Team Composition", font=('Helvetica', 14, 'bold')).grid(row=23, column=0, columnspan=2, pady=10)
team_name_label = ttk.Label(scrollable_frame, text="Team Name:")
team_name_label.grid(row=24, column=0, padx=5, pady=5, sticky='E')
team_name_entry = ttk.Entry(scrollable_frame)
team_name_entry.grid(row=24, column=1, padx=5, pady=5)

team_winrate_label = ttk.Label(scrollable_frame, text="Winrate:")
team_winrate_label.grid(row=25, column=0, padx=5, pady=5, sticky='E')
team_winrate_entry = ttk.Entry(scrollable_frame)
team_winrate_entry.grid(row=25, column=1, padx=5, pady=5)

add_team_button = ttk.Button(scrollable_frame, text="Add Team Composition", command=add_team_composition)
add_team_button.grid(row=26, column=0, columnspan=2, pady=10)

# Separator
ttk.Separator(scrollable_frame, orient='horizontal').grid(row=27, column=0, columnspan=2, sticky='ew', pady=10)

# Section: Assign Champion to Team Composition
ttk.Label(scrollable_frame, text="Assign Champion to Team Composition", font=('Helvetica', 14, 'bold')).grid(row=28, column=0, columnspan=2, pady=10)
team_comp_name_label = ttk.Label(scrollable_frame, text="Team Name:")
team_comp_name_label.grid(row=29, column=0, padx=5, pady=5, sticky='E')
team_comp_name_entry = ttk.Entry(scrollable_frame)
team_comp_name_entry.grid(row=29, column=1, padx=5, pady=5)

team_champion_id_label = ttk.Label(scrollable_frame, text="Champion ID:")
team_champion_id_label.grid(row=30, column=0, padx=5, pady=5, sticky='E')
team_champion_id_entry = ttk.Entry(scrollable_frame)
team_champion_id_entry.grid(row=30, column=1, padx=5, pady=5)

assign_team_champion_button = ttk.Button(scrollable_frame, text="Assign Champion", command=assign_champion_to_team)
assign_team_champion_button.grid(row=31, column=0, columnspan=2, pady=10)

# Tab 3: Display All Players
tab6 = ttk.Frame(notebook)
notebook.add(tab6, text='Players')

player_columns = ['Name', 'Winrate', 'Components', 'Augments', 'Champions', 'Team']
player_tree = ttk.Treeview(tab6, columns=player_columns, show='headings')
for col in player_columns:
    player_tree.heading(col, text=col)
    player_tree.column(col, width=150)
player_tree.pack(fill=tk.BOTH, expand=True, padx=10, pady=10)

populate_player_list(player_tree)

# Tab 4: Display Team Compositions
tab7 = ttk.Frame(notebook)
notebook.add(tab7, text='Teams')

team_columns = ['Team Name', 'Winrate', 'Champions']
team_tree = ttk.Treeview(tab7, columns=team_columns, show='headings')
for col in team_columns:
    team_tree.heading(col, text=col)
    team_tree.column(col, width=200)
team_tree.pack(fill=tk.BOTH, expand=True, padx=10, pady=10)

populate_team_list(team_tree)


# Tab: Champion Filter
tab_filter = ttk.Frame(notebook)
notebook.add(tab_filter, text='Champion Filter')


# Create variables and dictionaries to hold checkbuttons and their variables
effect_search_var = tk.StringVar()
effect_vars = {}
displayed_effects = {}
effects = get_all_effects()

origin_search_var = tk.StringVar()
origin_vars = {}
displayed_origins = {}
origins = get_all_origins()

class_search_var = tk.StringVar()
class_vars = {}
displayed_classes = {}
classes = get_all_classes()

role_search_var = tk.StringVar()
role_vars = {}
displayed_roles = {}
roles = get_all_roles()

# Match Type Variables
effect_match_type_var = tk.StringVar(value='Any')
origin_match_type_var = tk.StringVar(value='Any')
class_match_type_var = tk.StringVar(value='Any')
role_match_type_var = tk.StringVar(value='Any')

# Frame for filters
filter_frame = ttk.Frame(tab_filter)
filter_frame.pack(side='left', fill='both', expand=False, padx=10, pady=10)

# Frame for results
result_frame = ttk.Frame(tab_filter)
result_frame.pack(side='right', fill='both', expand=True, padx=10, pady=10)


# Filter button
filter_button = ttk.Button(filter_frame, text="Filter", command=filter_champions)
filter_button.pack(pady=5)

# Clear Filters button
clear_filters_button = ttk.Button(filter_frame, text="Clear Filters", command=clear_filters)
clear_filters_button.pack(pady=5)

# Create filter frames and setup checkbuttons
# Effects
effect_frame, effect_scrollable_frame = create_filter_frame(filter_frame, "Effects", effect_search_var)
# Effect Match Type
effect_match_type_label = ttk.Label(effect_frame, text="Match Type:")
effect_match_type_label.pack(pady=5)
effect_match_type_combobox = ttk.Combobox(effect_frame, textvariable=effect_match_type_var, values=['Any', 'All'], state='readonly')
effect_match_type_combobox.pack(pady=5)
setup_checkbuttons(effect_scrollable_frame, effects, effect_vars, displayed_effects)

# Origins
origin_frame, origin_scrollable_frame = create_filter_frame(filter_frame, "Origins", origin_search_var)
# Origin Match Type
origin_match_type_label = ttk.Label(origin_frame, text="Match Type:")
origin_match_type_label.pack(pady=5)
origin_match_type_combobox = ttk.Combobox(origin_frame, textvariable=origin_match_type_var, values=['Any', 'All'], state='readonly')
origin_match_type_combobox.pack(pady=5)
setup_checkbuttons(origin_scrollable_frame, origins, origin_vars, displayed_origins)

# Classes
class_frame, class_scrollable_frame = create_filter_frame(filter_frame, "Classes", class_search_var)
# Class Match Type
class_match_type_label = ttk.Label(class_frame, text="Match Type:")
class_match_type_label.pack(pady=5)
class_match_type_combobox = ttk.Combobox(class_frame, textvariable=class_match_type_var, values=['Any', 'All'], state='readonly')
class_match_type_combobox.pack(pady=5)
setup_checkbuttons(class_scrollable_frame, classes, class_vars, displayed_classes)

# Roles
role_frame, role_scrollable_frame = create_filter_frame(filter_frame, "Roles", role_search_var)
# Role Match Type
role_match_type_label = ttk.Label(role_frame, text="Match Type:")
role_match_type_label.pack(pady=5)
role_match_type_combobox = ttk.Combobox(role_frame, textvariable=role_match_type_var, values=['Any', 'All'], state='readonly')
role_match_type_combobox.pack(pady=5)
setup_checkbuttons(role_scrollable_frame, roles, role_vars, displayed_roles)

# Functions to update checkbuttons based on search
def update_effect_checkbuttons(*args):
    update_checkbuttons(effect_search_var, displayed_effects)

def update_origin_checkbuttons(*args):
    update_checkbuttons(origin_search_var, displayed_origins)

def update_class_checkbuttons(*args):
    update_checkbuttons(class_search_var, displayed_classes)

def update_role_checkbuttons(*args):
    update_checkbuttons(role_search_var, displayed_roles)

# Bind search variables
effect_search_var.trace('w', update_effect_checkbuttons)
origin_search_var.trace('w', update_origin_checkbuttons)
class_search_var.trace('w', update_class_checkbuttons)
role_search_var.trace('w', update_role_checkbuttons)

# Treeview to display results
columns = ['Name', 'Origins', 'Classes', 'Effects', 'Roles']
result_tree = ttk.Treeview(result_frame, columns=columns, show='headings')
for col in columns:
    result_tree.heading(col, text=col)
    result_tree.column(col, width=150)
result_tree.pack(fill=tk.BOTH, expand=True)

# Add a Label to display current filters
current_filters_label = ttk.Label(result_frame, text="Current Filters Applied:", justify='left')
current_filters_label.pack(pady=5, anchor='w')

# Add a Text widget to display the filters
filters_display = tk.Text(result_frame, height=6, wrap='word', state='disabled')
filters_display.pack(fill='x', padx=5, pady=5)

root.mainloop()