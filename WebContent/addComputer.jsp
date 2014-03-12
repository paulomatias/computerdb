<jsp:include page="include/header.jsp" />
<section id="main">

	<h1>Add Computer</h1>
	
	<form action="/ProjetWebExcilys/AddComputerServlet" method="POST">
		<fieldset>
			<div class="clearfix">
				<label for="name">Computer name:</label>
				<div class="input">
					<input type="text" name="name" />
					<span class="help-inline">Required</span>
				</div>
			</div>
	
			<div class="clearfix">
				<label for="introduced">Introduced date:</label>
				<div class="input">
					<input type="date" name="introducedDate" pattern="YY-dd-MM"/>
					<span class="help-inline">YYYY-dd-MM</span>
				</div>
			</div>
			<div class="clearfix">
				<label for="discontinued">Discontinued date:</label>
				<div class="input">
					<input type="date" name="discontinuedDate" pattern="YY-dd-MM"/>
					<span class="help-inline">YYYY-dd-MM</span>
				</div>
			</div>
			<div class="clearfix">
				<label for="company">Company Name:</label>
				<div class="input">
					<select name="company">
						<option value="0">--</option>
						<option value="1">Apple Inc.</option>
						<option value="2">Thinking Machines</option>
						<option value="3">RCA</option>
						<option value="4">Netronics</option>
						<option value="5">Tandy Corporation</option>
						<option value="6">Commodore International</option>
						<option value="7">MOS Technology</option>
						<option value="8">Micro Instrumentation and Telemetry Systems</option>
						<option value="9">IMS Associates, Inc.</option>
						<option value="10">Digital Equipment Corporation</option>
						<option value="11">Lincoln Laboratory</option>
						<option value="12">Moore School of Electrical Engineering</option>
						<option value="13">IBM</option>
						<option value="14">Amiga Corporation</option>
						<option value="15">Canon</option>
						<option value="16">Nokia</option>
						<option value="17">Sony</option>
						<option value="18">OQO</option>
						<option value="19">NeXT</option>
						<option value="20">Atari</option>
						<option value="21">Acorn computer</option>
						<option value="22">Timex Sinclair</option>
						<option value="23">Nintendo</option>
						<option value="24">Sinclair Research Ltd</option>
						<option value="25">Xerox</option>
						<option value="26">Hewlett-Packard</option>
						<option value="27">Zemmix</option>
						<option value="28">ACVS</option>
						<option value="29">Sanyo</option>
						<option value="30">Cray</option>
						<option value="31">Evans and Sutherland</option>
						<option value="32">E.S.R. Inc.</option>
						<option value="33">OMRON</option>
						<option value="34">BBN Technologies</option>
						<option value="35">Lenovo Group</option>
						<option value="36">ASUS</option>
						<option value="37">Amstrad</option>
						<option value="38">Sun Microsystems</option>
						<option value="39">Texas Instruments</option>
						<option value="40">HTC Corporation</option>
						<option value="41">Research In Motion</option>
						<option value="42">Samsung Electronics</option>
					</select>
				</div>
			</div>
		</fieldset>
		<div class="actions">
			<input type="submit" value="Add" class="btn primary">
			or <a href="dashboard.jsp" class="btn">Cancel</a>
		</div>
	</form>
</section>

<jsp:include page="include/footer.jsp" />