#!/usr/bin/python3
import os
import signal
import sys
import tempfile
import subprocess

import threading
import random
import datetime as dt

duedate = dt.datetime(2023, 9, 23, 23, 59, 59)

'''
This class takes the place of sys.stdout. It intercepts whatever is
being written to the console and writes it the console, a file, and
a local data structure.
In other words, whatever you print here or in your program also
prints to the file and to a list. The list can then be searched for 
proper output. 
'''
class Tee(object):
	def open(self, name, mode):
		sys.stdout.flush()
		self.file = open(name, mode)
		self.stdout = sys.stdout
		sys.stdout = self
	def close(self):
		sys.stdout = self.stdout
		self.file.close()
	def write(self, data):
		self.file.write(data)
		self.stdout.write(data)
	def flush(self):
		self.file.flush()
		self.stdout.flush()

def kill_proc(proc, timeout):
	# you must have done something wrong
	# to end up here
    timeout["value"] = True
    proc.kill()

# a run with timeout function 
def run_with_timeout(cmd, timeout_sec, stdinput):
    proc = subprocess.Popen(cmd,stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    timeout = {"value": False}
    timer = threading.Timer(timeout_sec, kill_proc, [proc, timeout])
    timer.start()
    (out, err) = proc.communicate(input = stdinput.encode("utf-8"))
    out = out.decode("utf-8")
    if len(out) < 1000:
        print(out)
    else:
    	#wow you printed a lot of stuff
        print(out[:1000] + "...")
    timer.cancel()
    return proc.returncode, timeout["value"], out

	
def run(cmd, time, stdinput="", testlist = [], scores = [2]):
	print("Running: {}\n".format(cmd))
	try:
		
		# just in case we will kill your process 
		# after <time> seconds
		res, timeout, out = run_with_timeout(cmd, time, stdinput)
	except:
		print("An exception occurred")
		print("{} test failed!".format(cmd))
		return 0
	if timeout:
		print("Taking too long, abort")
		print("Infinite loop?")
		return 0
	if res == 0:
		# program ran and nothing crashed. Check the output
		if len(testlist)==0:
			print("Test passed")
			return scores[0]
		score = 0
		index = 0
		for e in testlist:
			if out.find(e) == -1:
				print(e+" not found")
			else:
				print(e+" found, ",scores[index]," marks")
				score+=scores[index]
			index = index + 1
		return score
	else: 
		print("Program exited with non-zero status, test failed")
	return 0

def test(dirname, outof):

	
	#switch to directory dirname
	os.chdir(dirname)
	sys.stdout.flush()
	print("\n{stars}\n* Compilation\n{stars}".format(stars="*"*75))
	
	# run the 'make clean' and 'make all' commands
	cmds = ["clean","all"]
	for c in cmds:
		cmd = ["make",c]
		print("Executing: {}".format(" ".join(cmd)))
		res = subprocess.call(cmd)
		sys.stdout.flush()
		if res:
			# make command produced an error
			# return with 0 marks
			print("make: non-zero exit status {}".format(res))
			#print("\n\n{stars}\n* Mark: {mark}/{outof}\n".format(stars="*"*75, mark=0, outof=outof))
			#return
		
	score = 0
	
	#arguments are:
	#<command, timeout, input, find in output, score per string found>
	thorhealth = random.randint(10,20)
	snarlhealth = random.randint(10,20)
	thorattack = random.randint(1,5)
	snarlattack = random.randint(1,5)
	args = "Thor {} {} Snarl {} {}".format(thorhealth, thorattack, snarlhealth, snarlattack)
	thorhealth1 = thorhealth - snarlattack + 1
	snarlhealth1 = snarlhealth - thorattack - 1

	thorhealth2 = thorhealth1 - snarlattack - 1
	snarlhealth2 = snarlhealth1 - thorattack + 1

	answers = ["Thor", "Snarl", str(thorhealth), str(snarlhealth), 
			str(thorhealth1), str(snarlhealth1), 
			str(thorhealth2), str(snarlhealth2)]

	#answers = ["Thor", "Snarl", "10", "12", "8", "9", "6"]
	scores = [2,2,2,2,2,2,2,2]

	score += run("./p1", 5, args, answers, scores)

	'''now = dt.datetime.now()

	if now > duedate:
		print("Assignment is late, -10%")
		score = score * 0.9'''

	print("\n\n{stars}\n* Mark: {mark}/{outof}\n{stars}\n".format(stars="*"*75, mark=score, outof=outof))



	

def process_all():
			
	t = Tee()
	
	t.open('results.txt', 'w')
	dirname = 'tutorial2'
	#dirname = '.'
	test(dirname, 16)
		
	t.close()



if __name__ == "__main__":
	
	sys.stdout.flush()
	process_all()
	sys.stdout.flush()
	
