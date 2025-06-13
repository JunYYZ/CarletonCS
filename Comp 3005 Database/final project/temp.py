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
        # Insert into treeview
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
            SELECT GROUP_CONCAT(ch.name, ', ') FROM Champions ch
            JOIN Team_Composition_Champions tcc ON ch.championID = tcc.championID
            WHERE tcc.teamName = ?
        """, (teamName,))
        champions = cursor.fetchone()[0] or ''
        # Insert into treeview
        tree.insert('', tk.END, values=(teamName, winrate, champions))
    conn.close()

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
    team_name = team_name_entry.get()
    winrate = team_winrate_entry.get()
    if not team_name:
        messagebox.showwarning("Input Error", "Team name is required.")
        return
    conn = connect_db()
    cursor = conn.cursor()
    cursor.execute("SELECT teamName FROM Team_Composition WHERE teamName = ?", (team_name,))
    if cursor.fetchone():
        messagebox.showerror("Error", "Team composition already exists.")
        conn.close()
        return
    cursor.execute("""
        INSERT INTO Team_Composition (teamName, winrate)
        VALUES (?, ?)
    """, (team_name, winrate or None))
    conn.commit()
    conn.close()
    messagebox.showinfo("Success", f"Team composition '{team_name}' added successfully.")
    # Clear entries
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

# Function to assign champion to team composition
def assign_champion_to_team():
    team_name = team_comp_name_entry.get()
    champion_id = team_champion_id_entry.get()
    if not team_name or not champion_id:
        messagebox.showwarning("Input Error", "Both Team Name and Champion ID are required.")
        return
    conn = connect_db()
    cursor = conn.cursor()
    # Verify team exists
    cursor.execute("SELECT teamName FROM Team_Composition WHERE teamName = ?", (team_name,))
    team = cursor.fetchone()
    if not team:
        messagebox.showerror("Error", "Team composition not found.")
        conn.close()
        return
    # Verify champion exists
    cursor.execute("SELECT name FROM Champions WHERE championID = ?", (champion_id,))
    champion = cursor.fetchone()
    if not champion:
        messagebox.showerror("Error", "Champion not found.")
        conn.close()
        return
    # Assign champion to team composition
    cursor.execute("""
        INSERT OR IGNORE INTO Team_Composition_Champions (teamName, championID)
        VALUES (?, ?)
    """, (team_name, champion_id))
    conn.commit()
    conn.close()
    messagebox.showinfo("Success", f"Champion '{champion[0]}' assigned to team '{team_name}'.")
    # Clear entries
    team_comp_name_entry.delete(0, tk.END)
    team_champion_id_entry.delete(0, tk.END)

# Function to refresh tabs when selected
def on_tab_selected(event):
    selected_tab = event.widget.tab('current')['text']
    if selected_tab == 'Champions':
        populate_champion_list(champion_tree)
    elif selected_tab == 'Players':
        populate_player_list(player_tree)
    elif selected_tab == 'Teams':
        populate_team_list(team_tree)

# Initialize main window
root = tk.Tk()
root.title("TFT Database GUI")
root.geometry("1000x700")

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
    champion_tree.heading(col, text=col)
    champion_tree.column(col, width=80)
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

root.mainloop()
