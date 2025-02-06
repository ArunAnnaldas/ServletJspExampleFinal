import fitz  # PyMuPDF

def debug_extract_text(pdf_path):
    """ Extract and print text from a PDF file for debugging. """
    doc = fitz.open(pdf_path)
    for page_num in range(len(doc)):
        page = doc.load_page(page_num)
        text = page.get_text("text")  # Extract text
        print(f"\n--- Page {page_num + 1} ---\n")
        print(text)

pdf1 = "path/to/first.pdf"
pdf2 = "path/to/second.pdf"

print("Extracted text from PDF 1:")
debug_extract_text(pdf1)

print("\nExtracted text from PDF 2:")
debug_extract_text(pdf2)
