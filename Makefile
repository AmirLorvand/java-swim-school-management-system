JAVAC = javac
JAVA = java
SRC_DIR = src
OUT_DIR = out
MAIN_CLASS = swimschool.Main

SOURCES = $(SRC_DIR)/swimschool/Main.java 
$(SRC_DIR)/swimschool/SwimSchool.java 
$(SRC_DIR)/swimschool/SwimStudent.java 
$(SRC_DIR)/swimschool/SwimClass.java 
$(SRC_DIR)/swimschool/SwimInstructor.java 
$(SRC_DIR)/swimschool/Qualification.java 
$(SRC_DIR)/swimschool/DistanceSwim.java 
$(SRC_DIR)/swimschool/PersonalSurvival.java

all:
mkdir -p $(OUT_DIR)
$(JAVAC) -d $(OUT_DIR) $(SOURCES)

run: all
$(JAVA) -cp $(OUT_DIR) $(MAIN_CLASS)

clean:
rm -rf $(OUT_DIR)
